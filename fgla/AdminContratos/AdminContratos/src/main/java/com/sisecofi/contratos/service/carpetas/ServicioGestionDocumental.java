package com.sisecofi.contratos.service.carpetas;

import java.util.List;

import com.sisecofi.contratos.dto.DescargaSatCloudRequest;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

@SuppressWarnings("rawtypes")
public interface ServicioGestionDocumental {


	List<CarpetaDtoResponse> obtenerEstructuraDocumental(Long idProyecto);
	
	
	CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest);
	
	String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest);
	

	boolean eliminarArchivos(List<Archivo> eliminados);
	
	
	public Boolean cargarArchivoFaseIndividual(ArchivoCargadoFaseDto dto);


	
	List<CarpetaDtoResponse> obtenerEstructuraDocumentalConvenio(Long idConvenio);


	List<CarpetaDtoResponse> obtenerEstructuraDocReintegros(Long idContrato);
	
}
