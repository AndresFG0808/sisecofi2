package com.sisecofi.reportedocumental.dto.controldocumental.reintegros;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaReintegroDocumentalDto implements GenericReport {

	private BusquedaReintegroDocumental busquedaReintegroDocumental;
	private boolean acumuladaReintegro;
	private int pageReintegro;
	private int sizeReintegro;

	@Override
	public int getSize() {
		return sizeReintegro;
	}

	@Override
	public int getPage() {
		return pageReintegro;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaReintegro;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaReintegroDocumental;
	}

}
