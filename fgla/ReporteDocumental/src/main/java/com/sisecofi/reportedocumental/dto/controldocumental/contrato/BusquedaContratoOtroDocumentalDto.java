package com.sisecofi.reportedocumental.dto.controldocumental.contrato;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaContratoOtroDocumentalDto implements GenericReport {

	private BusquedaContratoOtroDocumental busquedaContratoOtroDocumental;
	private boolean acumuladaContratoOtro;
	private int pageContratoOtro;
	private int sizeContratoOtro;

	@Override
	public int getSize() {
		return sizeContratoOtro;
	}

	@Override
	public int getPage() {
		return pageContratoOtro;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaContratoOtro;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaContratoOtroDocumental;
	}

}
