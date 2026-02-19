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
        model.addAttribute("usuarios", resultado.objects);
        
        return "GetAll";
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
    public String DeleteDireccion(@PathVariable("IdDireccion") int IdDireccion, @PathVariable("IdUsuario") int IdUsuario){
        
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
        Resultado resultado = rolDAOImplementation.GetAll();
        model.addAttribute("roles", resultado.objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        return "Formulario";
    }
    
    @PostMapping("/form")
    public String Accion(@Valid @ModelAttribute("usuario")Usuario usuario, BindingResult bindingResult, @RequestParam("imagenFile") MultipartFile imageFile ,Model model){
        
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
        
        
        String[] nombreArchivo = imageFile.getOriginalFilename().split("\\.");
       
            if(nombreArchivo[1].equals("jpg") || nombreArchivo[1].equals("png")){
                
                try {
                    
                    usuario.setImagen(Base64.getEncoder().encodeToString(imageFile.getBytes()));
                    
                } catch (Exception ex) {
                    System.out.println("Problema con el archivo");
                    System.out.println("Error: "+ex);
                    return "Formulario";
                }
                
            } else if(imageFile != null){
                System.out.println("Archivo incorrecto");
                return "Formulario";
            }
               

            
        Resultado resultado = usuarioDAOImplementation.Add(usuario);
        
        if(resultado.correcto = false){
            System.out.println("Error al guardar los datos");
        }
        
        
        return "redirect:/usuario";
    }
    
    
    @GetMapping("/GetById/{IdUsuario}")
    public String GetById(@PathVariable("IdUsuario") int IdUsuario, Model model){
        model.addAttribute("usuario", usuarioDAOImplementation.GetById(IdUsuario).object);
        
        return "UsuarioDetalles";
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

