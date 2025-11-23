package com.sisecofi.admindevengados.service.gestion_documental;

import java.util.List;

import com.sisecofi.admindevengados.dto.DescargaSatCloudRequest;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;


public interface ServicioGestionDocumental {

	@SuppressWarnings("rawtypes")
	public List<CarpetaDtoResponse> obtenerEstructuraDocumental(Long idDictamen);
	
	
	public CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest);
	
	public String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest);
	
	
	public boolean eliminarArchivos(List<Archivo> eliminados);
	
	
	public Boolean cargarArchivoFaseIndividual(ArchivoCargadoFaseDto dto);


	public String descargarArchivo(String path);
	
}
