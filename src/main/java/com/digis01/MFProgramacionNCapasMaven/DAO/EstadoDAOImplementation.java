package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Estado;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class EstadoDAOImplementation implements IEstado{
    
    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado GetByID(int IdPais){
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL EstadoGetByPaisSP(?,?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
                resultado.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    resultado.objects.add(estado);
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
