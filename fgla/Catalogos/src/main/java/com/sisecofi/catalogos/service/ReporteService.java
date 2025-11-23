package com.sisecofi.catalogos.service;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ReporteService {

	byte[] obtenerReporte(int idCatalogo);

	byte[] obtenerAdministracion(int id);

	byte[] obtenerCentral(int id);

	byte[] obtenerMapas(int idCentral);

	byte[] obtenerAdministradoresGeneral(int id);

	byte[] obtenerAdministradoresCentral(int id);

	byte[] obtenerAdministradoresAdministracion(int id);
}
