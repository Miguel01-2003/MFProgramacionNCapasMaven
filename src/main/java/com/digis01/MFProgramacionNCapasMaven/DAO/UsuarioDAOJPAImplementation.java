package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {   
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Resultado GetAll() {
        Resultado resultado = new Resultado();
        try {
            
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();
            
            resultado.objects = new ArrayList<>();
            for (Usuario usuario : usuarios) {            
                resultado.objects.add(usuario);    
            }
            
            
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
    public Resultado Add(com.digis01.MFProgramacionNCapasMaven.ML.Usuario usuario) {
        Resultado resultado = new Resultado();
        ModelMapper modelMapper = new ModelMapper();
        try {
            
            Usuario usuarioJPA = modelMapper.map(usuario , Usuario.class);
            usuarioJPA.Direcciones.get(0).Usuario = usuarioJPA;
            usuarioJPA.setStatus(1);
            
            
            entityManager.persist(usuarioJPA);
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado GetById(int idusuario) {
        Resultado resultado = new Resultado();
        ModelMapper modelMapper = new ModelMapper();
        try {
            
            Usuario usuarioJPA = entityManager.find(Usuario.class, idusuario);
            
            com.digis01.MFProgramacionNCapasMaven.ML.Usuario usuario = modelMapper.map(usuarioJPA, com.digis01.MFProgramacionNCapasMaven.ML.Usuario.class);
            
            resultado.object = usuario;
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = true;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }
    
}
