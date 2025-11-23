package com.sisecofi.reportedocumental.dto.controldocumental.reintegros;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaReintegroOtroDocumentalDto implements GenericReport {

	private BusquedaReintegroOtroDocumental busquedaReintegroOtroDocumental;
	private boolean acumuladaReintegroOtro;
	private int pageReintegroOtro;
	private int sizeReintegroOtro;

	@Override
	public int getSize() {
		return sizeReintegroOtro;
	}

	@Override
	public int getPage() {
		return pageReintegroOtro;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaReintegroOtro;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaReintegroOtroDocumental;
	}

}
