package com.sisecofi.contratos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CierreContratoDto {

    @NotNull
    private String actaCierre;

    @NotNull
    private String nombreArchivo;

    @NotNull
    private Long idContrato;
}
