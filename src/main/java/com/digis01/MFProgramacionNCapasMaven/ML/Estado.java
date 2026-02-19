package com.digis01.MFProgramacionNCapasMaven.ML;

public class Estado {
    private int IdEstado;
    private String Nombre;
    public Pais Pais;

    //Getters
    public int getIdEstado() {
        return IdEstado;
    }

    public String getNombre() {
        return Nombre;
    }
    
    public Pais getPais(){
        return Pais;
    }
    
    //Setters
    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public void setPais(Pais Pais){
        this.Pais = Pais;
    }
    
    //Constructores
    public Estado() {
    }

    public Estado(int IdEstado, String Nombre, Pais Pais) {
        this.IdEstado = IdEstado;
        this.Nombre = Nombre;
        this.Pais = Pais;
    }
    
    
}
