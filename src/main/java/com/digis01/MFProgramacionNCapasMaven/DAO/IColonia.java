package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.ML.Resultado;

public interface IColonia {
    Resultado GetByMunicipio(int IdMunicipio);
//    Resultado GetCodigoPostal(int IdColonia);
}
