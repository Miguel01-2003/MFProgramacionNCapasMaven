package com.digis01.MFProgramacionNCapasMaven.Controller;

import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
@RequestMapping("demo")
public class DemoController {
    
    
    @GetMapping("saludo")
    public String Test(@RequestParam String nombre , Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    //http://localhost:8080/demo/saludo/Miguel%20Fernando
    @GetMapping("saludo/{nombre}")
    public String Test2(@PathVariable("nombre") String nombre , Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    //http://localhost:8080/demo/suma/1/2
    @GetMapping("suma/{numero1}/{numero2}")
    public String Test3 (@PathVariable int numero1, @PathVariable int numero2, Model model){
        model.addAttribute("numero1", numero1);
        model.addAttribute("numero2", numero2);
        int resultado = numero1 + numero2;
        model.addAttribute("resultado", resultado);
        return "suma";
    }
    
    //http://localhost:8080/demo/suma?numero1=1&numero2=2
    @GetMapping("suma")
    public String Test4 (@RequestParam int numero1, @RequestParam int numero2, Model model){
        model.addAttribute("numero1", numero1);
        model.addAttribute("numero2", numero2);
        int resultado = numero1 + numero2;
        model.addAttribute("resultado", resultado);
        return "suma";
    }
    //http://localhost:8080/demo/factorial?numero=5
    @GetMapping("factorial")
    public String Test5(@RequestParam int numero, Model model){
        int resultado = numero;
        if(numero==0){
            resultado = 1;
        }else{
            for (int i = numero; i >= 2; i--) {
                resultado = resultado * (i-1);
            }
        }
        
        model.addAttribute("numero", numero);
        model.addAttribute("resultado", resultado);
        return "factorial";
    }
    
//    @GetMapping("usuario")
//    public String Test6(Model model){
//        List<Usuario> usuarios = new ArrayList<>();
////        usuarios.add(new Usuario(1,"Migue","Miguel Fernando", "Pastrana", "Adame","mpastrana@email.com","password1",new Date(),"Masculino","7443114312"));
////        usuarios.add(new Usuario(2,"Nalid","Dilan", "Araiza", "Carbajal","daraiza@email.com","password1",new Date(),"Masculino","7443234312"));
////        usuarios.add(new Usuario(3,"Magenta","Arturo", "Cardenaz", "Rizo","acardenaz@email.com","password1",new Date(),"Masculino","7443678901"));
//        
//        model.addAttribute("usuarios", usuarios);
//        return "GetAll";
//    }
    
//    @GetMapping("usuario/formulario")
//    public String Test7(Model model){
//        return "Formulario";
//    }
    
    
    
    
}
