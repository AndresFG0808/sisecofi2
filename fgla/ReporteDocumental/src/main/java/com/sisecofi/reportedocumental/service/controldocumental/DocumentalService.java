package com.sisecofi.reportedocumental.service.controldocumental;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.PageDocumental;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface DocumentalService {

	PageDocumental buscarControlDocumental(BusquedaDocumentalDto busquedaDocumentalDto);

	byte[] descargarArchivo(BusquedaDocumentalDto busquedaDocumentalDto);

	byte[] descargarArchivos(Integer id, int identificador);

	byte[] descargarZip(BusquedaDocumentalDto busquedaDocumentalDto);

	List<CompartidoCloudModel> descargarCloud(BusquedaDocumentalDto busquedaDocumentalDto);

	boolean comprobarProyectos();
	
	@SuppressWarnings("rawtypes")
	List<CarpetaDtoResponse> obtenerMatriz(Integer identificador, Long idReferencia);
}
