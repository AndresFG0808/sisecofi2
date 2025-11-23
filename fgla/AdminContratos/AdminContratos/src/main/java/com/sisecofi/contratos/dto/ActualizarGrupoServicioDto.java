package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarGrupoServicioDto {
    private Long idGrupoServicio;
    private String grupo;
    private Integer idTipoConsumo;
}
