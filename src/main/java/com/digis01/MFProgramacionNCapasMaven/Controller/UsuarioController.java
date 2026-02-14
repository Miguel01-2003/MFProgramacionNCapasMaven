package com.digis01.MFProgramacionNCapasMaven.Controller;

import com.digis01.MFProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    
    @GetMapping
    public String Index(Model model){
        Resultado resultado = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuarios", resultado.objects);
        
        return "GetAll";
    }
    
    @GetMapping("form")
    public String Accion(Model model){
        model.addAttribute("usuario", new Usuario());
        Resultado resultado = rolDAOImplementation.GetAll();
        model.addAttribute("roles", resultado.objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        return "Formulario";
    }
    
    @PostMapping("form")
    public String Accion(@Valid @ModelAttribute("usuario")Usuario usuario, BindingResult bindingResult, Model model){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("usuario", usuario);
            return "Formulario";
        }
        
        return "redirect:/usuario";
    }
    
    
    @GetMapping("/GetById/IdUsuario")
    public String GetById(@RequestParam int IdUsuario, Model model){
        model.addAttribute("usuario", usuarioDAOImplementation.GetById(IdUsuario));
        
        return "Formulario";
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

