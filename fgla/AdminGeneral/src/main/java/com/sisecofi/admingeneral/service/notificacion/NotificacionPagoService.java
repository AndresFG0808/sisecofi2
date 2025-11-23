package com.sisecofi.admingeneral.service.notificacion;

import com.sisecofi.libreria.comunes.dto.plantillador.NotificacionDTO;

public interface NotificacionPagoService {
    byte[] generarDocumento(NotificacionDTO request);
}
