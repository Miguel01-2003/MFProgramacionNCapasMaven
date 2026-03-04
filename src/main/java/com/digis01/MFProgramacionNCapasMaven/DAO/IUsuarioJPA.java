package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;

public interface IUsuarioJPA {
    Resultado GetAll();
    
    Resultado Add(Usuario usuario);
    
    Resultado GetById(int idusuario);
}
