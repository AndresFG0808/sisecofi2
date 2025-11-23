package com.sisecofi.reportedocumental.dto.controldocumental.comite;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaComiteDocumentalDto implements GenericReport {

	private BusquedaComiteDocumental busquedaComiteDocumental;
	private boolean acumuladaComite;
	private int pageComite;
	private int sizeComite;

	@Override
	public int getSize() {
		return sizeComite;
	}

	@Override
	public int getPage() {
		return pageComite;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaComite;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaComiteDocumental;
	}

}
