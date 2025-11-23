package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleCM {
    private String numeroConvenio;
    private String tipoConvenio;
    private String fechaFirma;
    private String fechaFinServicio;
    private String fechaFinContratoCm;
    private String montoMaximoContratoCmConImpuestos;
    private String montoMaximoContratoCmSinImpuestos;
    private String montoPesos;
    private String comentarios;
}
