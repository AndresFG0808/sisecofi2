package com.sisecofi.reportedocumental.dto.controldocumental.notascredito;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaNotasDto implements GenericReport {

	private BusquedaNotas busquedaNotas;
	private boolean acumuladaNotas;
	private int pageNotas;
	private int sizeNotas;

	@Override
	public int getSize() {
		return sizeNotas;
	}

	@Override
	public int getPage() {
		return pageNotas;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaNotas;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaNotas;
	}

}
