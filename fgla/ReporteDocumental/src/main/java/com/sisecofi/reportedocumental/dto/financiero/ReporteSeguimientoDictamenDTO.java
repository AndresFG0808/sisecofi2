package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReporteSeguimientoDictamenDTO {
    private String nombreProyecto;
    private String nombreContrato;
    private String verificador;
    private String periodoControl;
    private String estatusDictamen;
    private BigDecimal importeFacturaSinImpuestos;
    private BigDecimal netoPagarUsd;
    private BigDecimal netoPagarPesos;
}
