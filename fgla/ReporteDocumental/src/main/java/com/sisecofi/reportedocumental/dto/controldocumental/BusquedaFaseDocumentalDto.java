package com.sisecofi.reportedocumental.dto.controldocumental;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaFaseDocumentalDto implements GenericReport {

	private BusquedaFaseDocumental busquedaFaseDocumental;
	private boolean acumuladaFaseDocumental;
	private int pageFaseDocumental;
	private int sizeFaseDocumental;

	@Override
	public int getSize() {
		return sizeFaseDocumental;
	}

	@Override
	public int getPage() {
		return pageFaseDocumental;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaFaseDocumental;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaFaseDocumental;
	}

}
