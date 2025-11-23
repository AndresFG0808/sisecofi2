package com.sisecofi.reportedocumental.service;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;
/**
 * 
 * @author omartinezj
 *
 */
public interface ServicioArchivo {

	void enviarArchivoSatCloudPapelera(PapeleraDto papeleraDto);
	
	void eliminarArchivoSatCloud(PapeleraDto eliminado);
	
	void restaurarArchivoPapelera(PapeleraDto restaurado);
}
