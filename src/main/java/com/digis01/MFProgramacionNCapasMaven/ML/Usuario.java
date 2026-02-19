package com.digis01.MFProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date; 
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {
    private int IdUsuario;
    
    
    private String UserName;
    
    @NotEmpty(message = "El nombre no debe estar vacio")
    @Size(min = 3, max = 50, message = "El nombre es muy corto")
    
    private String Nombre;
    
    @NotEmpty(message = "El apellido paterno no debe estar vacio")
    @Size(min = 3, max = 50, message = "Apellido invalido")
    private String ApellidoPaterno;
    
    
    //Correccion para permitir nulo
    @Size(min = 3, max = 50, message = "Apellido Invalido")
    private String ApellidoMaterno;
    
    @NotEmpty(message = "El campo email puede estar vacio")
    //@Email
    //@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$" , message = "Formato invalido")
    @Pattern.List({
        @Pattern(regexp = "^[^@]+@[^@]+$", message = "El correo debe contener un @"),
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$" , message = "Formato invalido")
//        @Pattern(regexp = "^[A-Za-z0-9]+([._%+-]?[A-Za-z0-9]+)*$", message = "El usuario es invalido"),
//        @Pattern(regexp = "^[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$", message = "El dominio es invalido"),
//        @Pattern(regexp = "^[a-zA-Z]{2,}$", message = "El correo debe contener un dominio "),
    })
    @Size(min = 5, message = "El correo es muy corto")
    private String Email;
    
    @Pattern.List({
        @Pattern(regexp = "(?=.*[0-9]).+", message = "La contraseña debe contener un numero."),
        @Pattern(regexp = "(?=.*[a-z]).+", message = "La contraseña debe contener una letra minuscula."),
        @Pattern(regexp = "(?=.*[A-Z]).+", message = "La contraseña debe tener una letra mayuscula."),
        @Pattern(regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+", message ="La contraseña debe contener un caracter especial."),
        @Pattern(regexp = "(?=\\S+$).+", message = "La contraseña no puede contener espacios.")
    })
    @Size(min = 8, max = 16, message = "La contraseña debe contener entre 8 y 16 caracteres")
    @NotEmpty(message = "La contraseña no puede estar vacia")
    private String Password;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@NotEmpty(message = "La fecha de nacimiento no puede estar vacia")
    private Date FechaNacimiento;  
    
    @NotEmpty(message = "El sexo no debe estar vacio")
    private String Sexo;
    
    
    @NotEmpty(message = "El telefono no debe estar vacio")
    @Pattern(regexp = "^\\d+$", message = "El telefono solo debe contener numeros")
    @Size(min = 10,max = 10, message = "El telefono debe contener 10 digitos")
    private String Telefono; 
    
    @Pattern(regexp = "^\\d+$", message = "El celular solo debe contener numeros")
    @Size(max = 10, message = "El celular debe contener 10 digitos")
    private String Celular;
    
    
//    @Pattern.List({
//        @Pattern(regexp = "")
//    })
    @Size(min = 18, max=18, message = "La curp contiene 18 caracteres")
    private String CURP;
    
    @Valid
    public Rol Rol;
    
    
    public List<Direccion> Direcciones;
    
    private String Imagen;
    
    //getters
    public int getIdUsuario(){
        return IdUsuario;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public String getPassword(){
        return Password;
    }
     
    public Date getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public String getSexo(){
        return Sexo;
    }
    
    public String getTelefono(){
        return Telefono;
    }
    
    public String getCelular(){
        return Celular;
    }
    
    public String getCURP(){
        return CURP;
    }
    
    public List<Direccion> getDirecciones(){
        return Direcciones;
    }
    
    public Rol getRol(){
        return Rol;
    }
    
    public String getImagen(){
        return Imagen;
    }
    
    
    
    //Setters
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
 
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public void setFechaNacimiento(Date FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }

    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    
    public void setCURP(String CURP){
        this.CURP = CURP;
    }
    
    public void setDirecciones(List<Direccion> Direcciones){
        this.Direcciones = Direcciones;
    }
    
    public void setRol(Rol Rol){
        this.Rol = Rol;
    }
    
    public void setImagen(String Imagen){
        this.Imagen = Imagen;
    }
    
    
    //Constructores
    public Usuario(){
        
    }
    

    public Usuario(int IdUsuario, String UserName, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Email, String Password, Date FechaNacimiento, String Sexo, String Telefono, String Celular, String CURP, Rol Rol, List<Direccion> Direcciones) {
        this.IdUsuario = IdUsuario;
        this.UserName = UserName;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Email = Email;
        this.Password = Password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.CURP = CURP;
        this.Rol = Rol;
        this.Direcciones = Direcciones;
    }

    public Usuario(int IdUsuario, String UserName, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Email, String Password, Date FechaNacimiento, String Sexo, String Telefono, String Celular, String CURP, Rol Rol, List<Direccion> Direcciones, String Imagen) {
        this.IdUsuario = IdUsuario;
        this.UserName = UserName;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Email = Email;
        this.Password = Password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.CURP = CURP;
        this.Rol = Rol;
        this.Direcciones = Direcciones;
        this.Imagen = Imagen;
    }
    
    

}
