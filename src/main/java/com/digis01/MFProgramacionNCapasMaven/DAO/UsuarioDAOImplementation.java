package com.digis01.MFProgramacionNCapasMaven.DAO;

import org.springframework.stereotype.Repository;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;
import com.digis01.MFProgramacionNCapasMaven.ML.Rol;
import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import com.digis01.MFProgramacionNCapasMaven.ML.Colonia;
import com.digis01.MFProgramacionNCapasMaven.ML.Municipio;
import com.digis01.MFProgramacionNCapasMaven.ML.Estado;
import com.digis01.MFProgramacionNCapasMaven.ML.Pais;
import java.sql.Date;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;


@Repository
public class UsuarioDAOImplementation implements IUsuario{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado GetAll(){
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionGetAllSP(?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.execute();
            
            ResultSet resultSet = (ResultSet)callableStatement.getObject(1);
            resultado.objects = new ArrayList<>();
            while(resultSet.next()){
                int IdUsuario = resultSet.getInt("IdUsuario");
                if(!resultado.objects.isEmpty() && IdUsuario == ((Usuario)(resultado.objects.get(resultado.objects.size()-1))).getIdUsuario()){
                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior")); 
                        
                    direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        
                    direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                        
                    direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                    
                    ((Usuario) (resultado.objects.get(resultado.objects.size() -1))).Direcciones.add(direccion);
                }else{
                    Usuario usuario = new Usuario();
                    usuario.Rol = new Rol();
                    usuario.setIdUsuario(IdUsuario);
                    usuario.setNombre(resultSet.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));//Opcional
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.Rol.setNombre(resultSet.getString("Rol"));
                    usuario.setImagen(resultSet.getString("Imagen"));
                    
                    usuario.setCelular(resultSet.getString("Celular"));//Opcional
                    usuario.setCURP(resultSet.getString("CURP"));//Opcional
                    
                    usuario.setUserName(resultSet.getString("UserName"));
                    usuario.setStatus(resultSet.getInt("Status"));
                    
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    int idDireccion = resultSet.getInt("IdDireccion");
                    if(idDireccion != 0){
                        usuario.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        
                        direccion.setIdDireccion(idDireccion);
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior")); 
                        
                        direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                        
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                        

                            usuario.Direcciones.add(direccion);
                        }

                        resultado.objects.add(usuario);
                }
            }
            
            
            return true;
        });
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        
        return resultado;
    }
    
    @Override
    public Resultado GetById(int IdUsuario){
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionGetByIdSP(?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
                if(resultSet.next()){
                    Usuario usuario = new Usuario();
                    usuario.Rol = new Rol();
                    
                    usuario.setIdUsuario(IdUsuario);
                    usuario.setNombre(resultSet.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));//Opcional
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuario.Rol.setNombre(resultSet.getString("Rol"));
                    usuario.setImagen(resultSet.getString("Imagen"));
                    
                    usuario.setCelular(resultSet.getString("Celular"));//Opcional
                    usuario.setCURP(resultSet.getString("CURP"));//Opcional
                    
                    usuario.setUserName(resultSet.getString("UserName"));
                    
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    int idDireccion = resultSet.getInt("IdDireccion");
                    
                    if(idDireccion != 0){
                        usuario.Direcciones = new ArrayList<>();

                        do{
                            Direccion direccion = new Direccion();
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior")); 
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                            direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));

                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));

                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                            usuario.Direcciones.add(direccion);
                        }while(resultSet.next());
                    }
                    resultado.object = usuario;
                }else{
                   resultado.correcto = false;
                   resultado.mensajeError = "El usuario con ese id no existe";
                }

                
                return true;
            });
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;    
    }
    
    @Override
    public Resultado Add(Usuario usuario){
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionADDSP(?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                
                //Agregar usuario
                
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(5, usuario.getUserName());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getPassword());
                callableStatement.setString(8, usuario.getSexo());
                callableStatement.setString(9, usuario.getTelefono());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCURP().toUpperCase());
                callableStatement.setInt(12, usuario.Rol.getIdRol());
                
                //Agregar imagen si existe
                callableStatement.setString(13, usuario.getImagen());
                
                //Agregar direccion
                callableStatement.setString(14, usuario.Direcciones.get(0).getCalle());
                callableStatement.setString(15, usuario.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString(16, usuario.Direcciones.get(0).getNumeroExterior());
                callableStatement.setInt(17, usuario.Direcciones.get(0).Colonia.getIdColonia());
                
                
                
                callableStatement.execute();
                
                
                return true;
            });
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        
        return resultado;
    }

    @Override
    public Resultado Delete(int IdUsuario) {
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionDeleteSP(?)}",(CallableStatementCallback<Boolean>)callableStatement->{
                callableStatement.setInt(1, IdUsuario);
                
                callableStatement.execute();
                
                return true;
            });
            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        
        return resultado;
    }

    @Override
    public Resultado Update(Usuario usuario, int IdUsuario) {
        Resultado resultado = new Resultado();
        
        try {
            
            jdbcTemplate.execute("{CALL UsuarioUpdateSP(?,?,?,?,?,?,?,?,?,?,?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdUsuario);
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3,usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setDate(5, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(6, usuario.getUserName());
                callableStatement.setString(7, usuario.getEmail());
                callableStatement.setString(8, usuario.getSexo());
                callableStatement.setString(9, usuario.getTelefono());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCURP());
                callableStatement.setInt(12, usuario.Rol.getIdRol());
                
                callableStatement.execute();
                
                return true;
                
            });
            
            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado UpdateImagen(String imagen, int IdUsuario) {
        Resultado resultado = new Resultado();
        
        try {
            
            jdbcTemplate.execute("{CALL UsuarioImagenUpdateSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{ 
            
                callableStatement.setInt(1, IdUsuario);
                callableStatement.setString(2, imagen);
                
                callableStatement.execute();                
                
                return true;
            });            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;     
    }

    @Override
    public Resultado BuscarUsuario(Usuario user) {
        Resultado resultado = new Resultado();
        
        try {
            
            jdbcTemplate.execute("{CALL BuscarUsuarioDireccion(?,?,?,?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setString(2, user.getNombre());
                callableStatement.setString(3, user.getApellidoPaterno());
                callableStatement.setString(4, user.getApellidoMaterno());
                callableStatement.setInt(5, user.Rol.getIdRol());
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(1);
                
                resultado.objects = new ArrayList<>();
                while(resultSet.next()){
                    int IdUsuario = resultSet.getInt("IdUsuario");
                    if(!resultado.objects.isEmpty() && IdUsuario == ((Usuario)(resultado.objects.get(resultado.objects.size()-1))).getIdUsuario()){
                        Direccion direccion = new Direccion();
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior")); 

                        direccion.Colonia.setNombre(resultSet.getString("Colonia"));

                        direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));

                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));

                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                        ((Usuario) (resultado.objects.get(resultado.objects.size() -1))).Direcciones.add(direccion);
                    }else{

                        Usuario usuario = new Usuario();
                        usuario.Rol = new Rol();
                        usuario.setIdUsuario(IdUsuario);
                        usuario.setNombre(resultSet.getString("Nombre"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));//Opcional
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.Rol.setNombre(resultSet.getString("Rol"));
                        usuario.setImagen(resultSet.getString("Imagen"));

                        usuario.setCelular(resultSet.getString("Celular"));//Opcional
                        usuario.setCURP(resultSet.getString("CURP"));//Opcional

                        usuario.setUserName(resultSet.getString("UserName"));

                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        int idDireccion = resultSet.getInt("IdDireccion");
                        if(idDireccion != 0){
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();

                            direccion.Colonia = new Colonia();
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();

                            direccion.setIdDireccion(idDireccion);
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior")); 

                            direccion.Colonia.setNombre(resultSet.getString("Colonia"));

                            direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));

                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));

                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));


                                usuario.Direcciones.add(direccion);
                            }

                            resultado.objects.add(usuario);
                    }
                }
                
                return true;
            });
            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        return resultado;
    }

    @Override
    public Resultado AddAll(List<Usuario> usuarios) {
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.batchUpdate("{CALL UsuarioDireccionADDSP(?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?,?)}",
                usuarios,
                usuarios.size(),
                    (callableStatement, usuario) ->{
                        callableStatement.setString(1, usuario.getNombre());
                        callableStatement.setString(2, usuario.getApellidoPaterno());
                        callableStatement.setString(3, usuario.getApellidoMaterno());
                        callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                        callableStatement.setString(5, usuario.getUserName());
                        callableStatement.setString(6, usuario.getEmail());
                        callableStatement.setString(7, usuario.getPassword());
                        callableStatement.setString(8, usuario.getSexo());
                        callableStatement.setString(9, usuario.getTelefono());
                        callableStatement.setString(10, usuario.getCelular());
                        callableStatement.setString(11, usuario.getCURP().toUpperCase());
                        callableStatement.setInt(12, usuario.Rol.getIdRol());

                        //Agregar imagen si existe
                        callableStatement.setString(13, usuario.getImagen());

                        //Agregar direccion
                        callableStatement.setString(14, usuario.Direcciones.get(0).getCalle());
                        callableStatement.setString(15, usuario.Direcciones.get(0).getNumeroInterior());
                        callableStatement.setString(16, usuario.Direcciones.get(0).getNumeroExterior());
                        callableStatement.setInt(17, usuario.Direcciones.get(0).Colonia.getIdColonia());
                        }
                    );
            
            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
        
    }

    @Override
    public Resultado UpdateStatus(int idUsuario, int status) {
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL UsuarioStatusUpdateSP(?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, idUsuario);
                callableStatement.setInt(2, status);
                
                callableStatement.execute();
                
                return true;
            });
            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        return resultado;
    }
}
