package com.sisecofi.reportedocumental.dto.controldocumental.dictamen;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaDictamenOtroDocumentalDto implements GenericReport {

	private BusquedaDictamenOtroDocumental busquedaDictamenOtroDocumental;
	private boolean acumuladaDictamenOtro;
	private int pageDictamenOtro;
	private int sizeDictamenOtro;

	@Override
	public int getSize() {
		return sizeDictamenOtro;
	}

	@Override
	public int getPage() {
		return pageDictamenOtro;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaDictamenOtro;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaDictamenOtroDocumental;
	}

}
