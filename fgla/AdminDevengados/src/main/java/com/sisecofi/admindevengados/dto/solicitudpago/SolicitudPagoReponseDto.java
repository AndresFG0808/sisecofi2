package com.sisecofi.admindevengados.dto.solicitudpago;

import com.sisecofi.libreria.comunes.model.dictamenes.SolicitudPagoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudPagoReponseDto extends SolicitudPagoModel {
    private Boolean estatusSolicitud;
    private Boolean estatusPagado;
}
