package com.sisecofi.reportedocumental.dto.controldocumental.contrato;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaContratoFaseDocumentalDto implements GenericReport {

	private BusquedaContratoFaseDocumental busquedaContratoFaseDocumental;
	private boolean acumuladaContratoFase;
	private int pageContratoFase;
	private int sizeContratoFase;

	@Override
	public int getSize() {
		return sizeContratoFase;
	}

	@Override
	public int getPage() {
		return pageContratoFase;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaContratoFase;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaContratoFaseDocumental;
	}

}
