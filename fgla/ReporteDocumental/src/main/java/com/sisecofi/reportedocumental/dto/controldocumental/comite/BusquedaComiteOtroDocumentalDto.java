package com.sisecofi.reportedocumental.dto.controldocumental.comite;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaComiteOtroDocumentalDto implements GenericReport {

	private BusquedaComiteOtroDocumental busquedaComiteNoDocumental;
	private boolean acumuladaComiteOtro;
	private int pageComiteOtro;
	private int sizeComiteOtro;

	@Override
	public int getSize() {
		return sizeComiteOtro;
	}

	@Override
	public int getPage() {
		return pageComiteOtro;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaComiteOtro;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaComiteNoDocumental;
	}

}
