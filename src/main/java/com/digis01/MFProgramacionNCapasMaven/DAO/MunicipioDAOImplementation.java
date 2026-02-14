package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Municipio;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MunicipioDAOImplementation implements IMunicipio{

    
    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado GetById(int IdEstado) {
        Resultado resultado = new Resultado();
 
        try {
            
            jdbcTemplate.execute("{CALL MunicipioGetByEstadoSP(?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdEstado);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
                resultado.objects = new ArrayList<>();
                    
                while(resultSet.next()){
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));
                    
                    resultado.objects.add(municipio);
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
