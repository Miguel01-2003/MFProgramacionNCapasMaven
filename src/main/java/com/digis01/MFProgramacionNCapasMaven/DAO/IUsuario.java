package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;
import com.digis01.MFProgramacionNCapasMaven.ML.Usuario;

public interface IUsuario {
    Resultado GetAll();
    Resultado GetById(int IdUsuario);
    
    Resultado Add(Usuario usuario);
}
