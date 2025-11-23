package com.sisecofi.libreria.comunes.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ActualizarTipoCambioDto {
    private Long idContrato;
    private BigDecimal tipoCambio;
}
