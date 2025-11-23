package com.sisecofi.libreria.comunes.dto.dictamen;

import java.math.BigDecimal;

public interface FacturasInformacion {

	Long getIdFactura();
	
	String getFolio();

	String getTipoMoneda();

	Long getIdDictamen();

	String getComprobanteFiscal();

	Boolean getConvenioColaboracion();

	String getEstatusFactura();

	String getEstatusDictamen();

	Long getIdProveedor();

	Long getIdContrato();

	BigDecimal getTipoCambioReferencial();

	BigDecimal getMontoCC();

	BigDecimal getMontoSat();

	BigDecimal getMontoPesosCC();

	BigDecimal getMontoPesosSat();

}
