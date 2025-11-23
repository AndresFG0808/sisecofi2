package com.sisecofi.contratos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ContratoMetaDto {
    private Long idContrato;
    private String nombreContrato;
    private String nombreProyecto;
    private String numeroContrato;
    private String proveedor;
    private String tipoProcedimiento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
    private String ultimoCm;
    private BigDecimal montoMaximo;
    private BigDecimal montoMacimoUltimoCm;
    private BigDecimal montoPesos;
    private String administracionCentral;
    private String administradorContrato;
}
