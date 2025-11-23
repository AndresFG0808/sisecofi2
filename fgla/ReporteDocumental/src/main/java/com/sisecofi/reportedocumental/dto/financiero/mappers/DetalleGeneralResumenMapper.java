package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleGeneralResumenMapper {
    private String idProyectoAgp;
    private String estatusProyecto;
    private Long idContrato;
    private String numeroContrato;
    private String nombreContrato;
    private String objetivoServicio;
    private String alcanceServicio;
    private String contratoVigente;
    private String dominio;
    private String tipoContratacion;
    private String proveedor;
    private String fechaInicioContrato;
    private String fechaFinContrato;
    private String fechaFinUltimoCm;
    private String minimoContratadoConImpuestosMxn;
    private String maximoContratadoConImpuestosMxn;
    private String maximoCmConImpuestosMxn;
    private String minimoContratadoSinImpuestosMxn;
    private String maximoContratadoSinImpuestosMxn;
    private String maximoCmSinImpuestosMxn;
    private String moneda;
    private String tipoCambio;
    private String minimoContratadoConImpuestos;
    private String maximoContratadoConImpuestos;
    private String maximoCmConImpuestos;
    private String administradorContrato;
    private String conveniosModificatorios;
    private BigDecimal mesesRestantesContrato;
    private String ultimaEstimacion;
    private String ultimoDictamen;
    private String ultimoPago;
}
