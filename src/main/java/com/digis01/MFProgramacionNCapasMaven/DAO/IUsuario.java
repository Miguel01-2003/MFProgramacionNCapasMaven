package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;
import java.util.List;

public interface IUsuario {
    Resultado GetAll();
    
    Resultado GetById(int IdUsuario);
    
    Resultado Add(Usuario usuario);
    
    Resultado Delete(int IdUsuario);
    
    Resultado Update(Usuario usuario, int IdUsuario);
    
    Resultado UpdateImagen(String imagen, int IdUsuario);
    
    Resultado BuscarUsuario(Usuario usuario);
    
    Resultado AddAll(List<Usuario> usuarios);
    
    Resultado UpdateStatus(int idUsuario, int status);
}
