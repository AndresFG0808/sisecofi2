package com.sisecofi.reportedocumental.dto.financiero.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleFacturaMapper {
    private String folioFactura;
    private String comprobanteFiscal;
    private String fechaFacturacion;
    private String fechaPago;
    private String moneda;
    private String tipoCambio;
    private String montoDolaresSat;
    private String montoDolaresCc;
    private String montoPesosSat;
    private String montoPesosCc;
    private String otroImpuestosSat;
    private String otroImpuestosCc;
    private String folioFichaPagoSat;
    private String folioFichaPagoCc;
    private String comentarios;
}
