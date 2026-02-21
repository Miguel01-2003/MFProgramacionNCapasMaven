package com.digis01.MFProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Min;

public class Rol {
    
      
    @Min(value = 1, message = "Seleccione algun rol valido")
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

    public Rol(int IdRol, String Nombre) {
        this.IdRol = IdRol;
        this.Nombre = Nombre;
    } 
}
