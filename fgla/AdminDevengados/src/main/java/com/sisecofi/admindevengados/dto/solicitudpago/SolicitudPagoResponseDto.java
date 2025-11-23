package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SolicitudPagoResponseDto {

    private Long idSolicitudPago;

    private String oficioSoliciturPago;

    private LocalDateTime fechaSolicitud;

    private String rutaSolicitudPago;

    private Boolean estatus;

    private Boolean aplicaConvenioColaboracion;

    private Boolean documentoGenerado;
    
    private Boolean moneda;

    private List<ReferenciaPagoResponseDto> facturaModelList;

    private boolean tieneSAT;

    private boolean tieneCC;
}
