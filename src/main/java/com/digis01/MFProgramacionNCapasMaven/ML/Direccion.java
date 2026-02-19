package com.digis01.MFProgramacionNCapasMaven.ML;

import com.digis01.MFProgramacionNCapasMaven.ML.Colonia;

public class Direccion {
    private int IdDireccion;
    private String Calle, NumeroInterior, NumeroExterior;
    public Colonia Colonia;
    
    //Getters
    public int getIdDireccion(){
        return IdDireccion;
    }
    
    public String getCalle(){
        return Calle;
    }
    
    public String getNumeroInterior(){
        return NumeroInterior;
    }
    
    public String getNumeroExterior(){
        return NumeroExterior;
    }
    
    public Colonia getColonia(){
        return Colonia;
    }
    
    //Setters
    public void setIdDireccion(int IdDireccion){
        this.IdDireccion = IdDireccion;
    }
    
    public void setCalle(String Calle){
        this.Calle = Calle;
    }
    
    public void setNumeroInterior(String NumeroInterior){
        this.NumeroInterior = NumeroInterior;
    }
    
    public void setNumeroExterior(String NumeroExterior){
        this.NumeroExterior = NumeroExterior;
    }
    
    public void setColonia(Colonia Colonia){
        this.Colonia = Colonia;
    }
    
    //Constructor
    public Direccion(){
        
    }

    public Direccion(int IdDireccion, String Calle, String NumeroInterior, String NumeroExterior, Colonia Colonia) {
        this.IdDireccion = IdDireccion;
        this.Calle = Calle;
        this.NumeroInterior = NumeroInterior;
        this.NumeroExterior = NumeroExterior;
        this.Colonia = Colonia;
    }
    
    
}
