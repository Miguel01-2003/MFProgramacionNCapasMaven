package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;

public interface IDireccionJPA {
    Resultado Add(Direccion direccion, int IdUsuario);
    
    Resultado GetById(int IdDireccion);
    
    Resultado Update(Direccion direccion, int IdDireccion);
    
    Resultado Delete(int IdDireccion);
}
