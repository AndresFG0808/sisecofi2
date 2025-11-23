package com.sisecofi.reportedocumental.dto.controldocumental.reintegros;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaReintegroFaseDocumentalDto implements GenericReport {

	private BusquedaReintegroFaseDocumental busquedaReintegroFaseDocumental;
	private boolean acumuladaReintegroFase;
	private int pageReintegroFase;
	private int sizeReintegroFase;

	@Override
	public int getSize() {
		return sizeReintegroFase;
	}

	@Override
	public int getPage() {
		return pageReintegroFase;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaReintegroFase;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaReintegroFaseDocumental;
	}

}
