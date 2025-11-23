package com.sisecofi.reportedocumental.dto.controldocumental.convenio;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaConvenioOtroDocumentalDto implements GenericReport {

	private BusquedaConvenioOtroDocumental busquedaConvenioOtroDocumental;
	private boolean acumuladaConvenioOtro;
	private int pageConvenioOtro;
	private int sizeConvenioOtro;

	@Override
	public int getSize() {
		return sizeConvenioOtro;
	}

	@Override
	public int getPage() {
		return pageConvenioOtro;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaConvenioOtro;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaConvenioOtroDocumental;
	}

}
