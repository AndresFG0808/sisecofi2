package com.sisecofi.reportedocumental.dto.controldocumental;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaNoDocumentalDto implements GenericReport {

	private BusquedaNoDocumental busquedaNoDocumental;
	private boolean acumuladaNoDocumental;
	private int pageNoDocumental;
	private int sizeNoDocumental;

	@Override
	public int getSize() {
		return sizeNoDocumental;
	}

	@Override
	public int getPage() {
		return pageNoDocumental;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaNoDocumental;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaNoDocumental;
	}
}
