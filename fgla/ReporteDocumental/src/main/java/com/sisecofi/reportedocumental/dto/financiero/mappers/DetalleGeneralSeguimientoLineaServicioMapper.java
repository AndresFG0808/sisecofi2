package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleGeneralSeguimientoLineaServicioMapper {
    private String nombreCortoProyecto;
    private String nombreCortoContrato;
    private String numeroContrato;
    private String estatusVolumetria;
    private Long numeroConsecutivoConceptoServicio;
    private String grupoServicio;
    private String conceptoServicio;
    private String tipoConsumo;
    private String tipoUnidad;
    private String precioUnitario;
    private String aplicaIva;
    private String aplicaIeps;
    private BigDecimal cantidadServiciosMinima;
    private BigDecimal cantidadServiciosMaxima;
    private BigDecimal cantidadServiciosMaximaUltimoCM;
}
