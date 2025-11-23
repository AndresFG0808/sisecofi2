package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ActualizarContratoDto {

    private Long idContrato;

    @NotNull(message = "El nombre de contrato no puede ser nulo")
    private String nombreContrato;

    @NotNull(message = "El nombre corto no puede ser nulo")
    private String nombreCortoContrato;

    @NotNull(message = "El proyecto asociado no puede ser nulo")
    private Long idProyecto;

    private String estatusContrato;

    private Integer idEstatusContrato;
}
