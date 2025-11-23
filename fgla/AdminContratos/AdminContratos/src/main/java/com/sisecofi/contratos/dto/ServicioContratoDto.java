package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServicioContratoDto {
    private Long idServicioContrato;
    private Long idGrupoServicio;
    private String grupo;
    private String tipoConsumo;
    private String claveProductosServicios;
    private String conseptosServicio;
    private Integer idTipoUnidad;
    private String tipoUnidad;
    private BigDecimal precioUnitario;
    private BigDecimal cantidadServiciosMinima;
    private BigDecimal cantidadServicios;
    private BigDecimal montoMinimo;
    private BigDecimal montoMaximo;
    private Boolean aplicaDns;
    private Boolean estatus;
    private Integer orden;
}
