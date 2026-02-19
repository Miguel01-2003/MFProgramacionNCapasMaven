package com.digis01.MFProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class Rol {
    
      
    @Positive(message = "Seleccione algun rol")
    private int IdRol;   
    
    private String Nombre;
    
    
    //Getters
    public int getIdRol(){
        return IdRol;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    //SEtters
    public void setIdRol(int IdRol){
        this.IdRol = IdRol;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    //Constructores
    public Rol(){
        
    }
}
