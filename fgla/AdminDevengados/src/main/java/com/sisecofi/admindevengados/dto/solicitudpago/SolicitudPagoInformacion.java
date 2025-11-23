package com.sisecofi.admindevengados.dto.solicitudpago;

import java.time.LocalDateTime;

public interface SolicitudPagoInformacion {
	
	Long getIdDictamen();	

	Long getIdSolicitudPago();

	String getOficioSoliciturPago();

	LocalDateTime getFechaSolicitud();

	String getRutaSolicitudPago();

	Boolean getEstatus();

	Boolean getAplicaConvenioColaboracion();

	Boolean getDocumentoGenerado();
	
	Long getIdContrato();
	
	Boolean getMoneda();

}
