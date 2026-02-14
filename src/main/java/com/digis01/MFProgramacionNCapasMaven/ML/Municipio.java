package com.digis01.MFProgramacionNCapasMaven.ML;

public class Municipio {
    private int IdMunicipio;
    private String Nombre;
    public Estado Estado;
    
    
    //Getters
    public int getIdMunicipio(){
        return IdMunicipio;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    //Setters
    public void setIdMunicipio(int IdMunicipio){
        this.IdMunicipio = IdMunicipio;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    //Constructores
    
    public Municipio(){
        
    }
}
