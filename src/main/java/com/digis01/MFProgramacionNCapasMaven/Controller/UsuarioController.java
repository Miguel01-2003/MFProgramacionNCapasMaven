package com.digis01.MFProgramacionNCapasMaven.Controller;

import com.digis01.MFProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;
import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;
    
    @GetMapping
    public String Index(Model model){
        Resultado resultado = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", resultado.objects);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
         
        return "GetAll";
    }
    
    @PostMapping
    public String Index(@ModelAttribute("usuario")Usuario usuario, RedirectAttributes redirectAttributes, Model model){
        
        Resultado resultado = usuarioDAOImplementation.BuscarUsuario(usuario);
        if(!resultado.objects.isEmpty()){
            model.addAttribute("usuarios", resultado.objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
             
            redirectAttributes.addFlashAttribute("mensajeExito","Usuarios encontrados");
            return "GetAll";
        }else{
            redirectAttributes.addFlashAttribute("mensajeError", "No se encontraron usuarios"+resultado.mensajeError);
            return "GetAll";
        }
        
        
    }
    
    @PostMapping("/delete/{IdUsuario}")
    public String EliminiarUsuario(@PathVariable("IdUsuario") int IdUsuario){
        Resultado resultado = usuarioDAOImplementation.Delete(IdUsuario);
        if(resultado.correcto){
            System.out.println("Borrado con exito");
        }else{
            System.out.println("Algo salio mal");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("Ex: " + resultado.ex);
        }
        
        return "redirect:/usuario";
    }
    
    @PostMapping("/GetById/{IdUsuario}/delete/{IdDireccion}")
    public String DeleteDireccion(@PathVariable("IdDireccion") int IdDireccion, 
            @PathVariable("IdUsuario") int IdUsuario){
        
        Resultado resultado = direccionDAOImplementation.Delete(IdDireccion);
        
        if(resultado.correcto){
            System.out.println("Direccion borrada con exito");
        }else{
            System.out.println("Algo salio mal");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("Ex: " + resultado.ex);
            
        }
        
        return "redirect:/usuario/GetById/"+IdUsuario;
    }
    
    @GetMapping("form")
    public String Accion(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        return "Formulario";
    }
    
    @PostMapping("/form")
    public String Accion(@Valid @ModelAttribute("usuario")Usuario usuario, BindingResult bindingResult, 
            @RequestParam("imagenFile") MultipartFile imageFile ,Model model, RedirectAttributes redirectAttributes){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles",rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises",paisDAOImplementation.GetAll().objects);
            
            int IdPais = usuario.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais();
            int IdEstado = usuario.Direcciones.get(0).Colonia.Municipio.Estado.getIdEstado();
            int IdMunicipio = usuario.Direcciones.get(0).Colonia.Municipio.getIdMunicipio();
        
            if(IdPais != 0){
                model.addAttribute("estados",estadoDAOImplementation.GetByID(IdPais).objects);
                
                if(IdEstado != 0){
                    model.addAttribute("municipios", municipioDAOImplementation.GetById(IdEstado).objects);
                    
                    if(IdMunicipio != 0){
                        model.addAttribute("colonias", coloniaDAOImplementation.GetByMunicipio(IdMunicipio).objects);
  
                    }
                }
            }
           
            return "Formulario";
        }
        
        //Validar Imagen
        if(imageFile != null){
            String[] nombreArchivo = imageFile.getOriginalFilename().split("\\.");
       
            if(nombreArchivo[1].equals("jpg") || nombreArchivo[1].equals("png")){
                
                try {
                    String imagen = Base64.getEncoder().encodeToString(imageFile.getBytes());
                    usuario.setImagen(imagen);
                    
                } catch (Exception ex) {
                    System.out.println("Problema con el archivo");
                    System.out.println("Error: "+ex);
                    return "Formulario";
                }
                
            } else if(imageFile != null){
                System.out.println("Archivo incorrecto");
                return "Formulario";
            }
        }

        //Resultado resultado = usuarioDAOImplementation.Add(usuario);
        Resultado resultado = new Resultado();
        resultado.correcto = true;
        
        if(resultado.correcto = true){
            System.out.println("El usuario se guardo correctamente");
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario agregado con exito");
            return "redirect:/usuario";
            
        }else{
            
            System.out.println("Error al guardar los datos");
            return "Formulario";
            
        }

    }
    
    
    @GetMapping("/GetById/{IdUsuario}")
    public String GetById(@PathVariable("IdUsuario") int IdUsuario, Model model){
        model.addAttribute("usuario", usuarioDAOImplementation.GetById(IdUsuario).object);
        model.addAttribute("roles",rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises",paisDAOImplementation.GetAll().objects);
        model.addAttribute("direccion", new Direccion());
        
        return "UsuarioDetalles";
    }
    
    @PostMapping("/GetById/{IdUsuario}")
    public String AddDireccion(@Valid @ModelAttribute("direccion")Direccion direccion, BindingResult bindingResult, 
            @PathVariable("IdUsuario") int IdUsuario, Model model, RedirectAttributes redirectAttributes){
        Resultado resultado = new Resultado();
        
        resultado = direccionDAOImplementation.Add(direccion, IdUsuario);
        
        if(resultado.correcto){
            System.out.println("Direccion agregada correctamente");
            redirectAttributes.addFlashAttribute("mensajeExito", "Direccion Agregada con exito");
        }else{
            System.out.println("Error al agregar direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al agregar la direccion "+ resultado.mensajeError);
        }
        
        return "redirect:/usuario/GetById/"+IdUsuario;
    }
    
    @GetMapping("/GetById/direccion/{IdDireccion}")
    @ResponseBody
    public Resultado GetDireccionesById(@PathVariable("IdDireccion") int IdDireccion){
        
        Resultado resultado = direccionDAOImplementation.GetById(IdDireccion);
        
        if(resultado.correcto){
            System.out.println("Direccion obtenida");
        }else{
            System.out.println("Error al obtener direccion");
            System.out.println("Mensaje de error: "+resultado.mensajeError);
            System.out.println("ex: "+resultado.ex);
        }
        
        return resultado;
    }
    
    @PostMapping("/GetById/{IdUsuario}/direccion/{IdDireccion}")
    public String UpdateDireccion(@Valid @ModelAttribute("direccion")Direccion direccion, BindingResult bindingResult, 
            @PathVariable("IdDireccion") int IdDireccion, @PathVariable("IdUsuario") int IdUsuario,
            Model model, RedirectAttributes redirectAttributes){
        
        Resultado resultado = direccionDAOImplementation.Update(direccion, IdDireccion);
        
        if(resultado.correcto){
            System.out.println("Direccion actualizada correctamente");
            redirectAttributes.addFlashAttribute("mensajeExito", "La direccion se actualizo correctamente");
        }else{
            System.out.println("Error al actualizar direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar direccion "+resultado.mensajeError);
        }
        
        return "redirect:/usuario/GetById/"+IdUsuario;
    }
    
    
    @PostMapping("/GetById/{IdUsuario}/editarImagen")
    public String UpdateImagen(@PathVariable("IdUsuario") int IdUsuario, @RequestParam("imagenFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes){
        
        String[] nombreArchivo = imageFile.getOriginalFilename().split("\\.");
        
        String imagen = null;
        if(imageFile != null){
            if(!nombreArchivo[0].isEmpty()){
                if(nombreArchivo[1].equals("jpg") || nombreArchivo[1].equals("png")){
                
                    try {
                        imagen = Base64.getEncoder().encodeToString(imageFile.getBytes());

                    } catch (Exception ex) {
                        System.out.println("Problema con el archivo");
                        System.out.println("Error: "+ex);
                        return "Formulario";
                    }
                }else{
                    System.out.println("Archivo incorrecto");
                    redirectAttributes.addFlashAttribute("mensajeError","Formato de archivo incorrecto");
                    return "redirect:/usuario/GetById/"+IdUsuario;
                }
            }
        }
        
           
            
        Resultado resultado = usuarioDAOImplementation.UpdateImagen(imagen, IdUsuario);

        if(resultado.correcto){
            System.out.println("Imagen modificada con exito");
            redirectAttributes.addFlashAttribute("mensajeExito", "Imagen actulizada con exito");
        }else{
            System.out.println("Error al agregar direccion");
            System.out.println("Mensaje de error: "+resultado.mensajeError);
            System.out.println("ex: "+resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError","Error al actualizar la imagen " + resultado.mensajeError);
        }
            
        return "redirect:/usuario/GetById/"+IdUsuario;
    }
    
    @PostMapping("/GetById/{IdUsuario}/editarUsuario")
    public String UpdateUsuario(@Valid @ModelAttribute("usuario")Usuario usuario, BindingResult bindingResult, 
            @PathVariable("IdUsuario") int IdUsuario, Model model, RedirectAttributes redirectAttributes){
        
        Resultado resultado = usuarioDAOImplementation.Update(usuario, IdUsuario);
        if(resultado.correcto){
            System.out.println("Usuario modificado con exito");
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario actualizado con exito");
        }else{
            System.out.println("Error al agregar direccion");
            System.out.println("Mensaje de error: "+resultado.mensajeError);
            System.out.println("ex: "+resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar usuario "+ resultado.mensajeError);
        }
        
        return "redirect:/usuario/GetById/"+IdUsuario;
    }
    
    @GetMapping("getEstadosByPais/{IdPais}")
    @ResponseBody
    public Resultado GetEstadosByPais(@PathVariable("IdPais") int IdPais){
        Resultado resultado = estadoDAOImplementation.GetByID(IdPais);
        
        return resultado;
    }
    
    @GetMapping("getEstadosByPais/{IdPais}/{IdEstado}")
    @ResponseBody
    public Resultado GetMunicipiosByEstado(@PathVariable("IdEstado") int IdEstado){
        Resultado resultado = municipioDAOImplementation.GetById(IdEstado);
        
        return resultado;
    }
    
    @GetMapping("getEstadosByPais/{IdPais}/{IdEstado}/{IdMunicipio}")
    @ResponseBody
    public Resultado GetColoniaByEstados(@PathVariable("IdMunicipio") int IdMunicipio){
        Resultado resultado = coloniaDAOImplementation.GetByMunicipio(IdMunicipio);
        
        return resultado;
    }
    
    
}        

