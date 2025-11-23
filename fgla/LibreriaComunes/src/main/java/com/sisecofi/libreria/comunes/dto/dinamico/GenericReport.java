package com.sisecofi.libreria.comunes.dto.dinamico;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface GenericReport {

	int getSize();

	int getPage();

	boolean isAcumulada();

	Object getDataReporteDto();
}
