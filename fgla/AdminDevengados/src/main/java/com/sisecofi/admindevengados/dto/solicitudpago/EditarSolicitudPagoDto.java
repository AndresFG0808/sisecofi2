package com.sisecofi.admindevengados.dto.solicitudpago;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

@Getter
@Setter
public class EditarSolicitudPagoDto {
    private Long idSolicitudPago;

    private String oficioSolicitudPago;

    private LocalDateTime fechaSolicitud;

    private String archivoPdf;

    private String nombreArchivo;

    private Boolean documentoGenerado;

    private DictamenId idDictamen;

}
