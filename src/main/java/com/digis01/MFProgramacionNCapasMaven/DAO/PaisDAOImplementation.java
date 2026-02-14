package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Pais;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPais{
    
    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado GetAll(){
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL PaisGetAllSP(?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(1);
                resultado.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    
                    resultado.objects.add(pais);
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
