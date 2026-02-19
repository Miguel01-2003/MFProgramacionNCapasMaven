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
    
    public Estado getEstado(){
        return Estado;
    }
    
    //Setters
    public void setIdMunicipio(int IdMunicipio){
        this.IdMunicipio = IdMunicipio;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public void setEstado(Estado Estado){
        this.Estado = Estado;
    }
    
    //Constructores
    
    public Municipio(){
        
    }

    public Municipio(int IdMunicipio, String Nombre, Estado Estado) {
        this.IdMunicipio = IdMunicipio;
        this.Nombre = Nombre;
        this.Estado = Estado;
    }
    
    
}
