package com.sisecofi.reportedocumental.dto.controldocumental.contrato;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaContratoDocumentalDto implements GenericReport {

	private BusquedaContratoDocumental cotratoDocumental;
	private boolean acumuladaContrato;
	private int pageContrato;
	private int sizeContrato;

	@Override
	public int getSize() {
		return sizeContrato;
	}

	@Override
	public int getPage() {
		return pageContrato;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaContrato;
	}

	@Override
	public Object getDataReporteDto() {
		return cotratoDocumental;
	}

}
