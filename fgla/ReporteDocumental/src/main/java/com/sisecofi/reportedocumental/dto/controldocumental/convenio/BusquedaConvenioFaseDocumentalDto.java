package com.sisecofi.reportedocumental.dto.controldocumental.convenio;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaConvenioFaseDocumentalDto implements GenericReport {

	private BusquedaConvenioFaseDocumental convenioFaseDocumental;
	private boolean acumuladaConvenioFase;
	private int pageConvenioFase;
	private int sizeConvenioFase;

	@Override
	public int getSize() {
		return sizeConvenioFase;
	}

	@Override
	public int getPage() {
		return pageConvenioFase;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaConvenioFase;
	}

	@Override
	public Object getDataReporteDto() {
		return convenioFaseDocumental;
	}

}
