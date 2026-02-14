package com.digis01.MFProgramacionNCapasMaven.ML;

public class Pais {
    private int IdPais;
    private String Nombre;

    //Getters
    public int getIdPais() {
        return IdPais;
    }

    public String getNombre() {
        return Nombre;
    }

    //Setters
    public void setIdPais(int IdPais) {
        this.IdPais = IdPais;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    //Constructores
    public Pais() {
    }

    public Pais(int IdPais, String Nombre) {
        this.IdPais = IdPais;
        this.Nombre = Nombre;
    }
    
    
            
    
    
}
