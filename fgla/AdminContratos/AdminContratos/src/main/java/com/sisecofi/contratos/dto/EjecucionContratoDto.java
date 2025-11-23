package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EjecucionContratoDto {

    private  Long idContrato;

    private LocalDateTime fechaInicioVigenciaServicios;

    private LocalDateTime fechaFinVigenciaServicios;

    private LocalDateTime fechaInicioVigenciaContrato;

    private LocalDateTime fechaFinVigenciaContrato;

    private Integer idTipoMoneda;

    private Double tipoCambioMaximo;

    private Double iva;

    private Double porcentajeIEPS;

    private Double montoMinimoSinImpuestos;

    private Double montoMaximoSinImpuestos;

    private Double montoPesosSinImpuestos;

    private Double montoMinimoConImpuestos;

    private Double montoMaximoConImpuestos;

    private Double montoPesosConImpuestos;


}
