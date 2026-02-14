package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class RolDAOImplementation implements IRol{
    
    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado GetAll(){
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL RolGetAllSP(?)}",(CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(1);
                resultado.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Rol rol = new Rol();
                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setNombre(resultSet.getString("Nombre"));
                    
                    resultado.objects.add(rol);
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
