package com.sisecofi.libreria.comunes.dto.contrato;

import com.sisecofi.libreria.comunes.model.catalogo.CatIeps;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VigenciaMontoDto {

    private long vigenciaMontoConteo;
    private VigenciaMontosModel vigenciaMontosModel;
    private CatIva iva;
    private CatIeps ieps;
}
