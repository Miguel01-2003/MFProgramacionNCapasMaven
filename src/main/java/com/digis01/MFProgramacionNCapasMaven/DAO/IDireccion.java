package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Direccion;
import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;

public interface IDireccion {
    Resultado Add(Direccion direccion, int IdUsuario);
    
    Resultado Delete(int IdDireccion);
}
