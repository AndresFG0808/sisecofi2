package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReporteConvenioModificatorioDto {
    private String numeroConvenio;
    private String tipo;
    private LocalDateTime fechaFirma;
    private LocalDateTime fechaFin;
    private BigDecimal montoMaximo;
}
