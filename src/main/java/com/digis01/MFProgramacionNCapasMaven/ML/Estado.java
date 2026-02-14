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
    //Setters
    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    //Constructores
    public Estado() {
    }
    
    
}
