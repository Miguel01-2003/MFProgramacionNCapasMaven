package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;

public interface IEstado {
    Resultado GetByID(int IdPais);
}
