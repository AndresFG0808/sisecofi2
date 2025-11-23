package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DictaminadaPagadaDTO extends VolumetriaBaseDTO {
    private BigDecimal cantidadServiciosSat;
    private String montoSat;
    private BigDecimal cantidadServiciosCc;
    private String montoCc;
}
