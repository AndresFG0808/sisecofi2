package com.sisecofi.admindevengados.service;

import org.springframework.web.multipart.MultipartFile;
import com.sisecofi.admindevengados.dto.IdSoporteDocumentalDto;
import com.sisecofi.admindevengados.dto.SoporteDocumentalDto;
import com.sisecofi.admindevengados.model.SoporteDocumentalModel;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;

public interface SoporteDocumentalService {

	String cargarArchivo(Long dictamenId, MultipartFile files);

	SoporteDocumentalModel obtenerSoporteDocumental(Long dictamenId);

	SoporteDocumentalModel guardarOActualizarSoporteDocumental(SoporteDocumentalDto soporteDocumental,
			MultipartFile detallePenasDeducciones);
	
	SoporteDocumentalModel actualizarEntregablese(IdSoporteDocumentalDto idSoporteDocumental, MultipartFile entregables);
	
	SoporteDocumentalModel actualizarOficio(IdSoporteDocumentalDto idSoporteDocumental, MultipartFile oficio);

	CarpetaCompartidaDto descargarFolderSatCloud(String path, Long dictamenId);

	String descargarArchivo(String path, Long dictamenId);

	Dictamen dictaminado(Long dictamenId);

	Boolean validarSiExistenPenasDeducciones(Long dictamenId);

	Boolean validarResponsabilidad(Long idContrato);

	Boolean crearPista(Long idSoporteDocumental, Long idDictamen, Boolean estatus);

	Boolean validarResponsabilidadSoporteDictaminado(Long dictamenId);

	Boolean validarResponsabilidadFechaRecepcion(Long dictamenId);


}
