package com.sisecofi.reportedocumental.dto.controldocumental.convenio;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaConvenioDocumentalDto implements GenericReport {

	private BusquedaConvenioDocumental convenioDocumental;
	private boolean acumuladaConvenio;
	private int pageConvenio;
	private int sizeConvenio;

	@Override
	public int getSize() {
		return sizeConvenio;
	}

	@Override
	public int getPage() {
		return pageConvenio;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaConvenio;
	}

	@Override
	public Object getDataReporteDto() {
		return convenioDocumental;
	}

}
