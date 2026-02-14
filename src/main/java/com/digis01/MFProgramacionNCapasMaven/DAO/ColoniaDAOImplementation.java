package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Colonia;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColonia{

    
    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    @Override
    public Resultado GetByMunicipio(int IdMunicipio) {
        Resultado resultado = new Resultado();
        
        try {
            jdbcTemplate.execute("{CALL ColoniaGetByMunicipioSP(?,?)}",(CallableStatementCallback<Boolean>)callableStatement ->{
                callableStatement.setInt(1, IdMunicipio);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
                resultado.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    
                    resultado.objects.add(colonia);
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

//    @Override
//    public Resultado GetCodigoPostal(int IdColonia) {
//        Resultado resultado = new Resultado();
//        
//        try {
//            
////            jdbcTemplate.execute("{CALL ColoniaGetByMunicipioSP(?,?)}",(CallableStatementCallback<Boolean>)callableStatement ->{
////                callableStatement.setInt(1, pIdColonia);
////                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
////                callableStatement.execute();
////                
////                ResultSet resultSet = (ResultSet)callableStatement.getObject(2);
////                resultado.objects = new ArrayList<>();
////                
////                while(resultSet.next()){
////                    Colonia colonia = new Colonia();
////                    
////                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
////                    colonia.setNombre(resultSet.getString("Nombre"));
////                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
////                    
////                    resultado.objects.add(colonia);
////                }
////                
////                
////                return true;
////            });
//            
//            resultado.correcto = true;
//        } catch (Exception ex) {
//            resultado.correcto = false;
//            resultado.mensajeError = ex.getLocalizedMessage();
//            resultado.ex = ex;
//        }
//        
//        return resultado;
//    }
    
}
