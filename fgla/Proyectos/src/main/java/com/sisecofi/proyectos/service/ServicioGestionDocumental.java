package com.sisecofi.proyectos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.proyectos.dto.DescargaSatCloudRequest;
import com.sisecofi.proyectos.dto.GestionDocumentalRequest;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;


public interface ServicioGestionDocumental {

	@SuppressWarnings("rawtypes")
	List<CarpetaDtoResponse> obtenerEstructuraDocumental(Long idProyecto);
	
	Boolean guardarTabla (GestionDocumentalRequest archivos);
	
	CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest);
	
	String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest);
	
	boolean eliminarArchivos(List<Archivo> eliminados);
	
	
	public Boolean cargarArchivoFaseIndividual(ArchivoCargadoFaseDto dto);
	
}
