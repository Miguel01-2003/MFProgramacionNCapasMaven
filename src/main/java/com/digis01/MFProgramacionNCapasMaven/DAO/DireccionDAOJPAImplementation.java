package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOJPAImplementation implements IDireccionJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public Resultado Add(com.digis01.MFProgramacionNCapasMaven.ML.Direccion direccion, int IdUsuario) {
        Resultado resultado = new Resultado();
         ModelMapper modelMapper = new ModelMapper();
        try {
            
            Direccion direccionJPA = modelMapper.map(direccion, Direccion.class);
            direccionJPA.Usuario = new Usuario();
            direccionJPA.Usuario.setIdUsuario(IdUsuario);
            
            entityManager.persist(direccionJPA);
            
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
        ModelMapper modelMapper = new ModelMapper();
        try {
            
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            
            com.digis01.MFProgramacionNCapasMaven.ML.Direccion direccion = 
                    modelMapper.map(direccionJPA, com.digis01.MFProgramacionNCapasMaven.ML.Direccion.class);
            
            resultado.object = direccion;
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    @Transactional
    public Resultado Update(com.digis01.MFProgramacionNCapasMaven.ML.Direccion direccion, int IdDireccion) {
        Resultado resultado = new Resultado();
        ModelMapper modelMapper = new ModelMapper();
        try {
            
            Direccion direccionJPA = entityManager.merge(entityManager.find(Direccion.class, IdDireccion));
            System.out.println("Calle(Antigua): "+direccionJPA.getCalle());
            direccionJPA = modelMapper.map(direccion, Direccion.class);
            System.out.println("Calle(Nueva): "+direccionJPA.getCalle());
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado Delete(int IdDireccion) {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
    