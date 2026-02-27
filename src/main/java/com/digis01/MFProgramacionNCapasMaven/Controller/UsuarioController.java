package com.digis01.MFProgramacionNCapasMaven.Controller;

import com.digis01.MFProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.ML.Colonia;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;
import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import com.digis01.MFProgramacionNCapasMaven.ML.ErroresArchivo;
import com.digis01.MFProgramacionNCapasMaven.ML.Rol;
import com.digis01.MFProgramacionNCapasMaven.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    
    @Autowired
    private ValidationService validationService;

    @GetMapping
    public String Index(Model model) {
        Resultado resultado = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", resultado.objects);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);

        return "GetAll";
    }

    @PostMapping
    public String Index(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes, Model model) {
        Resultado resultado = usuarioDAOImplementation.BuscarUsuario(usuario);
        model.addAttribute("usuarios", resultado.objects);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        if (!resultado.objects.isEmpty()) {

            redirectAttributes.addFlashAttribute("mensajeExito", "Usuarios encontrados");
            return "GetAll";
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "No se encontraron usuarios" + resultado.mensajeError);
            return "GetAll";
        }
    }

    @PostMapping("/delete/{IdUsuario}")
    public String EliminiarUsuario(@PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes) {
        Resultado resultado = usuarioDAOImplementation.Delete(IdUsuario);
        if (resultado.correcto) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario eliminado con exito");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al eliminar al usuario: " + resultado.mensajeError);
        }

        return "redirect:/usuario";
    }

    @PostMapping("/GetById/{IdUsuario}/delete/{IdDireccion}")
    public String DeleteDireccion(@PathVariable("IdDireccion") int IdDireccion,
            @PathVariable("IdUsuario") int IdUsuario) {

        Resultado resultado = direccionDAOImplementation.Delete(IdDireccion);

        if (resultado.correcto) {
            System.out.println("Direccion borrada con exito");
        } else {
            System.out.println("Algo salio mal");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("Ex: " + resultado.ex);

        }

        return "redirect:/usuario/GetById/" + IdUsuario;
    }

    @GetMapping("form")
    public String Accion(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        return "Formulario";
    }

    @PostMapping("/form")
    public String Accion(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
            @RequestParam("imagenFile") MultipartFile imageFile, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

            int IdPais = usuario.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais();
            int IdEstado = usuario.Direcciones.get(0).Colonia.Municipio.Estado.getIdEstado();
            int IdMunicipio = usuario.Direcciones.get(0).Colonia.Municipio.getIdMunicipio();

            if (IdPais != 0) {
                model.addAttribute("estados", estadoDAOImplementation.GetByID(IdPais).objects);

                if (IdEstado != 0) {
                    model.addAttribute("municipios", municipioDAOImplementation.GetById(IdEstado).objects);

                    if (IdMunicipio != 0) {
                        model.addAttribute("colonias", coloniaDAOImplementation.GetByMunicipio(IdMunicipio).objects);

                    }
                }
            }

            return "Formulario";
        }

        //Validar Imagen
        if (imageFile != null) {
            String[] nombreArchivo = imageFile.getOriginalFilename().split("\\.");

            if (nombreArchivo[1].equals("jpg") || nombreArchivo[1].equals("png")) {

                try {
                    String imagen = Base64.getEncoder().encodeToString(imageFile.getBytes());
                    usuario.setImagen(imagen);

                } catch (Exception ex) {
                    System.out.println("Problema con el archivo");
                    System.out.println("Error: " + ex);
                    return "Formulario";
                }

            } else if (imageFile != null) {
                System.out.println("Archivo incorrecto");
                return "Formulario";
            }
        }

        Resultado resultado = usuarioDAOImplementation.Add(usuario);

        if (resultado.correcto = true) {
            System.out.println("El usuario se guardo correctamente");
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario agregado con exito");
            return "redirect:/usuario";

        } else {

            System.out.println("Error al guardar los datos");
            return "Formulario";

        }

    }

    @GetMapping("/GetById/{IdUsuario}")
    public String GetById(@PathVariable("IdUsuario") int IdUsuario, Model model) {
        model.addAttribute("usuario", usuarioDAOImplementation.GetById(IdUsuario).object);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        model.addAttribute("direccion", new Direccion());

        return "UsuarioDetalles";
    }

    @PostMapping("/GetById/{IdUsuario}")
    public String AddDireccion(@Valid @ModelAttribute("direccion") Direccion direccion, BindingResult bindingResult,
            @PathVariable("IdUsuario") int IdUsuario, Model model, RedirectAttributes redirectAttributes) {
        Resultado resultado = new Resultado();

        resultado = direccionDAOImplementation.Add(direccion, IdUsuario);

        if (resultado.correcto) {
            System.out.println("Direccion agregada correctamente");
            redirectAttributes.addFlashAttribute("mensajeExito", "Direccion Agregada con exito");
        } else {
            System.out.println("Error al agregar direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al agregar la direccion " + resultado.mensajeError);
        }

        return "redirect:/usuario/GetById/" + IdUsuario;
    }

    @GetMapping("/GetById/direccion/{IdDireccion}")
    @ResponseBody
    public Resultado GetDireccionesById(@PathVariable("IdDireccion") int IdDireccion) {

        Resultado resultado = direccionDAOImplementation.GetById(IdDireccion);

        if (resultado.correcto) {
            System.out.println("Direccion obtenida");
        } else {
            System.out.println("Error al obtener direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
        }

        return resultado;
    }

    @PostMapping("/GetById/{IdUsuario}/direccion/{IdDireccion}")
    public String UpdateDireccion(@Valid @ModelAttribute("direccion") Direccion direccion, BindingResult bindingResult,
            @PathVariable("IdDireccion") int IdDireccion, @PathVariable("IdUsuario") int IdUsuario,
            Model model, RedirectAttributes redirectAttributes) {

        Resultado resultado = direccionDAOImplementation.Update(direccion, IdDireccion);

        if (resultado.correcto) {
            System.out.println("Direccion actualizada correctamente");
            redirectAttributes.addFlashAttribute("mensajeExito", "La direccion se actualizo correctamente");
        } else {
            System.out.println("Error al actualizar direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar direccion " + resultado.mensajeError);
        }

        return "redirect:/usuario/GetById/" + IdUsuario;
    }

    @PostMapping("/GetById/{IdUsuario}/editarImagen")
    public String UpdateImagen(@PathVariable("IdUsuario") int IdUsuario, @RequestParam("imagenFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        String[] nombreArchivo = imageFile.getOriginalFilename().split("\\.");

        String imagen = null;
        if (imageFile != null) {
            if (!nombreArchivo[0].isEmpty()) {
                if (nombreArchivo[1].equals("jpg") || nombreArchivo[1].equals("png")) {

                    try {
                        imagen = Base64.getEncoder().encodeToString(imageFile.getBytes());

                    } catch (Exception ex) {
                        System.out.println("Problema con el archivo");
                        System.out.println("Error: " + ex);
                        return "Formulario";
                    }
                } else {
                    System.out.println("Archivo incorrecto");
                    redirectAttributes.addFlashAttribute("mensajeError", "Formato de archivo incorrecto");
                    return "redirect:/usuario/GetById/" + IdUsuario;
                }
            }
        }

        Resultado resultado = usuarioDAOImplementation.UpdateImagen(imagen, IdUsuario);

        if (resultado.correcto) {
            System.out.println("Imagen modificada con exito");
            redirectAttributes.addFlashAttribute("mensajeExito", "Imagen actulizada con exito");
        } else {
            System.out.println("Error al agregar direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar la imagen " + resultado.mensajeError);
        }

        return "redirect:/usuario/GetById/" + IdUsuario;
    }

    @PostMapping("/GetById/{IdUsuario}/editarUsuario")
    public String UpdateUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
            @PathVariable("IdUsuario") int IdUsuario, Model model, RedirectAttributes redirectAttributes) {

        Resultado resultado = usuarioDAOImplementation.Update(usuario, IdUsuario);
        if (resultado.correcto) {
            System.out.println("Usuario modificado con exito");
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario actualizado con exito");
        } else {
            System.out.println("Error al agregar direccion");
            System.out.println("Mensaje de error: " + resultado.mensajeError);
            System.out.println("ex: " + resultado.ex);
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar usuario " + resultado.mensajeError);
        }

        return "redirect:/usuario/GetById/" + IdUsuario;
    }

    @GetMapping("getEstadosByPais/{IdPais}")
    @ResponseBody
    public Resultado GetEstadosByPais(@PathVariable("IdPais") int IdPais) {
        Resultado resultado = estadoDAOImplementation.GetByID(IdPais);

        return resultado;
    }

    @GetMapping("getEstadosByPais/{IdPais}/{IdEstado}")
    @ResponseBody
    public Resultado GetMunicipiosByEstado(@PathVariable("IdEstado") int IdEstado) {
        Resultado resultado = municipioDAOImplementation.GetById(IdEstado);

        return resultado;
    }

    @GetMapping("getEstadosByPais/{IdPais}/{IdEstado}/{IdMunicipio}")
    @ResponseBody
    public Resultado GetColoniaByEstados(@PathVariable("IdMunicipio") int IdMunicipio) {
        Resultado resultado = coloniaDAOImplementation.GetByMunicipio(IdMunicipio);

        return resultado;
    }

    @GetMapping("/cargamasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, RedirectAttributes redirectAttributes,  HttpSession session) {
        try {
            if (archivo != null) {
                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];
                List<Usuario> usuarios = null;
                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(new File(rutaArchivo));

                } else if (extension.equals("xlsx")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
                    
                } else {
                    System.out.println("Error: Formato incorrecto");
                    
                }
                List<ErroresArchivo> errores = ValidarDatos(usuarios);
                
                if(errores.isEmpty()){
                    String uuid = UUID.randomUUID().toString();
                    
                    model.addAttribute("usuarios", usuarios);
                    session.setAttribute(uuid, rutaArchivo);
                    model.addAttribute("llave",uuid);
                }else{
                    model.addAttribute("errores", errores);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error(Cargamasiva):"+ex.getLocalizedMessage());
        }

        return "CargaMasiva";
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        List<Usuario> usuarios;
        try (InputStream inputStream = new FileInputStream(archivo);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            usuarios = new ArrayList<>();
            String columna = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            simpleDateFormat.setLenient(false);
            while ((columna = bufferedReader.readLine()) != null) {
                System.out.println(columna);
                String[] atributosColumna = columna.split("\\|");
                Usuario usuario = new Usuario();
                usuario.Rol = new Rol();
                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.Colonia = new Colonia();
                
                usuario.setUserName(atributosColumna[0]);
                
                usuario.setNombre(atributosColumna[1]);
                usuario.setApellidoPaterno(atributosColumna[2]);
                if(atributosColumna[3].length()>0){
                     usuario.setApellidoMaterno(atributosColumna[3]);//Opcional
                }
                usuario.setEmail(atributosColumna[4]);
                usuario.setPassword(atributosColumna[5]);              
                usuario.setFechaNacimiento(simpleDateFormat.parse(atributosColumna[6]));
                usuario.setSexo(atributosColumna[7]);
                usuario.setTelefono(atributosColumna[8]);
                
                if(atributosColumna[9].length()>0){
                    usuario.setCelular(atributosColumna[9]);//Opcional
                }
                if(atributosColumna[10].length()>0){
                    usuario.setCURP(atributosColumna[10]);//Opcional
                }
                
                usuario.Rol.setIdRol(Integer.parseInt(atributosColumna[11]));
                
                direccion.setCalle(atributosColumna[12]);
                
                if(atributosColumna[13].length()>0){
                    direccion.setNumeroInterior(atributosColumna[13]);//Opcional
                }
                
                direccion.setNumeroExterior(atributosColumna[14]);
                direccion.Colonia.setIdColonia(Integer.parseInt(atributosColumna[15]));
                
                usuario.Direcciones.add(direccion);
                
                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            System.out.println("Exepcion: "+ex.getLocalizedMessage());
            return null;
            
        }

        return usuarios;
    }
    
    public List<Usuario> LecturaArchivoXLSX(File archivo){
        List<Usuario> usuarios = null;
        try (InputStream inputStream = new FileInputStream(archivo);
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            usuarios = new ArrayList<>();
            for(Row row: sheet){
                Usuario usuario = new Usuario();
                usuario.Rol = new Rol();
                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.Colonia = new Colonia();
                DataFormatter formatter = new DataFormatter();
                
                usuario.setUserName(row.getCell(0).getStringCellValue());
                usuario.setNombre(row.getCell(1).getStringCellValue());
                usuario.setApellidoPaterno(row.getCell(2).getStringCellValue());
                
                if(row.getCell(3) != null){
                     usuario.setApellidoMaterno(row.getCell(3).getStringCellValue());//Opcional
                }
                
                usuario.setEmail(row.getCell(4).getStringCellValue());
                usuario.setPassword(row.getCell(5).getStringCellValue());
                usuario.setFechaNacimiento(row.getCell(6).getDateCellValue());
                usuario.setSexo(row.getCell(7).getStringCellValue());
                usuario.setTelefono(formatter.formatCellValue(row.getCell(8)));
                
                if(row.getCell(9) != null){//Siempre marca verdadero
                    usuario.setCelular(formatter.formatCellValue(row.getCell(9)));//Opcional
                }
               
                if(row.getCell(10)!= null){
                    usuario.setCURP(row.getCell(10).getStringCellValue());//Opcional
                }
                
                usuario.Rol.setIdRol((int)Math.round(row.getCell(11).getNumericCellValue()));
                
                direccion.setCalle(row.getCell(12).getStringCellValue());
                
                if(row.getCell(13) != null){
                    direccion.setNumeroInterior(formatter.formatCellValue(row.getCell(13)));//Opcional
                }

                direccion.setNumeroExterior(formatter.formatCellValue(row.getCell(14)));
                direccion.Colonia.setIdColonia((int)Math.round(row.getCell(15).getNumericCellValue()));
                
                usuario.Direcciones.add(direccion);
                
                usuarios.add(usuario);
            }
            
            
        } catch (Exception ex) {
            System.out.println("Exepcion: "+ex.getLocalizedMessage());
            return null;
        }
        return usuarios;
    }
    
    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios){
        List<ErroresArchivo> errores = new ArrayList<>();
        int fila = 0;
        for(Usuario usuario: usuarios){
            BindingResult bindingResult = validationService.ValidateObject(usuario);
            fila++;
            if (bindingResult.hasErrors()) { 
                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    ErroresArchivo erroresArchivo = new ErroresArchivo();               
                    erroresArchivo.dato = objectError.getObjectName();
                    erroresArchivo.descripcion = objectError.getDefaultMessage();
                    erroresArchivo.fila = fila;
                    errores.add(erroresArchivo);
                }
            }
        }
        
        return errores;
    }
    
    @GetMapping("/cargamasiva/procesar/{llave}")
    public String ProcesarCargaMasiva(@PathVariable("llave") String llave, RedirectAttributes redirectAttributes, HttpSession session){
        Resultado resultado = null;
        String rutaArchivo = session.getAttribute(llave).toString();
        
        File archivo = new File(rutaArchivo);
        String extension = archivo.getName().split("\\.")[1];
        List<Usuario> usuarios = null;
        if(extension.equals("txt")){
            usuarios = LecturaArchivoTxt(archivo);
            resultado = usuarioDAOImplementation.AddAll(usuarios);
            if(resultado.correcto){
                redirectAttributes.addFlashAttribute("mensajeExito", "Usuarios ingresados con exito");
            }else{
                redirectAttributes.addFlashAttribute("mensajeError", "Error al ingresar los usuarios");
                System.out.println("Error: " + resultado.mensajeError);
                System.out.println("Ex: "+resultado.ex);
            }
        }else if(extension.equals("xlsx")){
            usuarios = LecturaArchivoXLSX(archivo);
            resultado = usuarioDAOImplementation.AddAll(usuarios);
            if(resultado.correcto){
                redirectAttributes.addFlashAttribute("mensajeExito", "Usuarios ingresados con exito");
            }else{
                redirectAttributes.addFlashAttribute("mensajeError", "Error al ingresar los usuarios");
                System.out.println("Error: " + resultado.mensajeError);
                System.out.println("Ex: "+resultado.ex);
            }
        }else{
            resultado.correcto = false;
            resultado.mensajeError = "Extension de archivo incorrecta";
            redirectAttributes.addFlashAttribute("mensajeError", "Extencion de archivo incorrecta - ¿Como llegaste aqui?");
        }
        
        return "redirect:/usuario";
    }

    @PostMapping("/actualizar/Status/{IdUsuario}/{Status}")
    @ResponseBody
    public Resultado ActualizarStatusUsuario(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("Status") int Status){
        
        Resultado resultado = usuarioDAOImplementation.UpdateStatus(IdUsuario, Status);
        
        return resultado;
    }



}
