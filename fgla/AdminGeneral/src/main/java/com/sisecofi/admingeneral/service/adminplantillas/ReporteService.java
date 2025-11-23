package com.sisecofi.admingeneral.service.adminplantillas;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ReporteService {

	byte[] generarReporte(Integer idPlantilla);

	byte[] generarReporteBase();

}
