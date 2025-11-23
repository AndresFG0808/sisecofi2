package com.sisecofi.admindevengados.dto.solicitudpago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReferenciaPagoConvenioInformacion {
	
	Long getIdReferenciaPago();
    Integer getIdTipoNotificacionPago();
    Long getIdSolicitudPago();
    String getOficioNotificacionPago();
    String getFolioFichaPago();
    LocalDateTime getFechaPago();
    LocalDateTime getFechaNotificacion();
    BigDecimal getTipoCambioPagado();
    BigDecimal getPagadoNAFIN();
    String getRutaFichaNAFIN();
    Integer getIdDesglose();
    Boolean getEstatusPagado();
    Boolean getConvenioColaboracion();

}
