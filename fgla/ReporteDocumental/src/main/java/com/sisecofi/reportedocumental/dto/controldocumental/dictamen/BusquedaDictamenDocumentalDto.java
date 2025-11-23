package com.sisecofi.reportedocumental.dto.controldocumental.dictamen;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaDictamenDocumentalDto implements GenericReport {

	private BusquedaDictamenDocumental busquedaDictamenDocumental;
	private boolean acumuladaDictamen;
	private int pageDictamen;
	private int sizeDictamen;

	@Override
	public int getSize() {
		return sizeDictamen;
	}

	@Override
	public int getPage() {
		return pageDictamen;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaDictamen;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaDictamenDocumental;
	}

}
