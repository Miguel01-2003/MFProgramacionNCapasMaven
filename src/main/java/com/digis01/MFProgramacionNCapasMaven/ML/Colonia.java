package com.digis01.MFProgramacionNCapasMaven.ML;

public class Colonia {
    private int IdColonia;
    private String Nombre, CodigoPostal;
    public Municipio Municipio;
    
    //Getters
    public int getIdColonia(){
        return IdColonia;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    
    public Municipio getMunicipio(){
        return Municipio;
    }
    
    //Setters
    public void setIdColonia(int IdColonia){
        this.IdColonia = IdColonia;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }
    
    public void setMunicipio(Municipio Municipio){
        this.Municipio = Municipio;
    }
    
    
    //Constructores
    public Colonia(){
        
    }

    public Colonia(int IdColonia, String Nombre, String CodigoPostal, Municipio Municipio) {
        this.IdColonia = IdColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
        this.Municipio = Municipio;
    }
    
    
}
