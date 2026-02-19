package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
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
    
}
