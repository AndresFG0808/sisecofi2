package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ObtenerContratoDto {
    private Long idContrato;
    private Long idProyecto;
    private Boolean estatus;
    private String nombreContrato;
    private  String nombreCortoContrato;
    private LocalDateTime ultimaModificacion;
}
