package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SolicitudPagoDto {

    private Long idDictamen;

    private Long idSolicitudPago;

    private String oficioSolicitudPago;

    private LocalDateTime fechaSolicitud;

    private Boolean solicitudPago;

    private String archivoPdf;

    private String nombreArchivo;

    private Boolean documentoGenerado;
}
