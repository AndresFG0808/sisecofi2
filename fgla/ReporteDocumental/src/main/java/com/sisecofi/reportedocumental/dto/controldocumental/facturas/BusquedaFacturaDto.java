package com.sisecofi.reportedocumental.dto.controldocumental.facturas;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class BusquedaFacturaDto implements GenericReport {

	private BusquedaFactura busquedaFactura;
	private boolean acumuladaFactura;
	private int pageFactura;
	private int sizeFactura;

	@Override
	public int getSize() {
		return sizeFactura;
	}

	@Override
	public int getPage() {
		return pageFactura;
	}

	@Override
	public boolean isAcumulada() {
		return acumuladaFactura;
	}

	@Override
	public Object getDataReporteDto() {
		return busquedaFactura;
	}

}
