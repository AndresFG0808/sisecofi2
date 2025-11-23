package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlaneadaEstimadaDTO extends VolumetriaBaseDTO {
    private BigDecimal cantidadServicios;
    private String monto;
}
