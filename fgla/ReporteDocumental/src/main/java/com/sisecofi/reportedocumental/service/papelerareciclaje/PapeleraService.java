package com.sisecofi.reportedocumental.service.papelerareciclaje;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.reportedocumental.dto.papelera.BusquedaPapeleraDto;
import com.sisecofi.reportedocumental.dto.papelera.PagePapelera;

public interface PapeleraService {

	PagePapelera obtenerReporte(BusquedaPapeleraDto busquedaPapeleraDto);
	
	boolean eliminarArchivos(List<Archivo> eliminados);
	
	boolean eliminarArchivosPapelera(List<PapeleraDto> eliminados);
	
	boolean restaurarArchivoPapelera(List<PapeleraDto> archivosPapelera);
 
	byte[] exportarExcel(BusquedaPapeleraDto busquedaPapeleraDto);
	
	byte[] descargarZip(BusquedaPapeleraDto busquedaPapeleraDto);
	
	public byte[] descargarArchivo(Long idPapelera);

	List<CompartidoCloudModel> descargarCloud(BusquedaPapeleraDto busquedaPapeleraDto);
}
