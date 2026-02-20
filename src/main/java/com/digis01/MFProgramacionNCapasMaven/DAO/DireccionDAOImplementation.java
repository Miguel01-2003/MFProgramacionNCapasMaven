package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Colonia;
import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import com.digis01.MFProgramacionNCapasMaven.ML.Estado;
import com.digis01.MFProgramacionNCapasMaven.ML.Municipio;
import com.digis01.MFProgramacionNCapasMaven.ML.Pais;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DireccionDAOImplementation implements IDireccion{

    
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado Add(Direccion direccion, int IdUsuario) {
        Resultado resultado = new Resultado();
        
        try{
            
            jdbcTemplate.execute("{CALL DireccionAddSP(?,?,?,?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdUsuario);
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setString(4, direccion.getNumeroExterior());
                callableStatement.setInt(5, direccion.Colonia.getIdColonia());
                
                callableStatement.execute();
                
                
                return true;
            });
            
            resultado.correcto = true;
        }catch(Exception ex){
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado Delete(int IdDireccion) {
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL DireccionDeleteSP(?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.setInt(1, IdDireccion);
                
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
    public Resultado GetById(int IdDireccion) {
        Resultado resultado = new Resultado();
        
        try {
            
            jdbcTemplate.execute("{CALL DireccionGetByIdSP(?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdDireccion);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(1);
                
                if(resultSet.next()){
                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    
                    direccion.setIdDireccion(IdDireccion);
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSet.getString("colonia"));
                    direccion.Colonia.setCodigoPostal(resultSet.getString("codigopostal"));
                    
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                    
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                    
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("Idpais"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                    
                    resultado.object = direccion;
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
    
}
