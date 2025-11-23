package com.sisecofi.contratos.dto;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.contratos.GrupoServiciosModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoServicioDto {
    private GrupoServiciosModel grupoServiciosModel;
    private BaseCatalogoModel tipoConsumo;
}
