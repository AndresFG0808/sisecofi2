package com.sisecofi.admindevengados.dto.solicitudpago;

import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenciaPagoConvenioDto extends ReferenciaPagoModel {

    private Long idFactura;
    private String folio;
    private String tipoMoneda;
    private String nombreArchivo;
    private boolean tieneSAT;
    private boolean tieneCC;
}
