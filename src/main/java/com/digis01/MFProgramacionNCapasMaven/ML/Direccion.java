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
    
    //Constructor
    public Direccion(){
        
    }
}
