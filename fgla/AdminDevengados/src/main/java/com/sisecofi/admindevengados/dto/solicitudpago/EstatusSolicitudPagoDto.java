package com.sisecofi.admindevengados.dto.solicitudpago;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstatusSolicitudPagoDto {
    private Long idSolicitudPago;
    private DictamenId idDictamen;
    private Boolean estatusSolicitud;
    private String nombreArchivo;
    private String archivo;
}
