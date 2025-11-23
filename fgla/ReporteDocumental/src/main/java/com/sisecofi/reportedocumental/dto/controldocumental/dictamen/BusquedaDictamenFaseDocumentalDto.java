package com.sisecofi.reportedocumental.dto.controldocumental.dictamen;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaDictamenFaseDocumentalDto implements GenericReport {

	private BusquedaDictamenFaseDocumental busquedaDictamenFaseDocumental;
	private boolean acumuladaDictamenFase;
	private int pageDictamenFase;
	private int sizeDictamenFase;

	@Override
	public int getSize() {
		return sizeDictamenFase;
	}

	@Override
	public int getPage() {
		return pageDictamenFase;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaDictamenFase;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaDictamenFaseDocumental;
	}

}
