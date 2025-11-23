package com.sisecofi.admingeneral.service.adminplantillas;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaCarpetasDto;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sisecofi.admingeneral.dto.adminplantillas.CarpetaDtoResponse;
import com.sisecofi.admingeneral.dto.adminplantillas.PlantillaResponseDto;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdminPlantillaService {

	@SuppressWarnings("rawtypes")
	PlantillaDto<CarpetaDtoResponse> lecturaPlantilla(MultipartFile file, int idFase);

	@SuppressWarnings("rawtypes")
	PlantillaResponseDto guardarPlantilla(PlantillaDto<CarpetaDtoResponse> plantilla)throws JsonProcessingException;

	@SuppressWarnings("rawtypes")
	PlantillaDto<CarpetaDtoResponse> obtenerPlantilla(Integer idPlantilla);

	List<PlantillaVigenteModel> obtenerPlantillas();

	@SuppressWarnings("rawtypes")
	PlantillaVigenteModel actualizarPlantilla(PlantillaDto<CarpetaDtoResponse> plantilla, Integer idPlantillaVigente)throws JsonProcessingException;

	List<PlantillaVigenteModel> obtenerPlantillasFase(Integer idFaseProyecto);

	PlantillaVigenteModel obtenterPlantillaVigente(Integer idPlantilla);
	
	PlantillaVigenteModel guardarEstatusPlantilla (boolean estatus, Integer idPlantilla);

	PlantillaCarpetasDto<CarpetaPlantillaModel> obtenerPlantillaGeneral(Integer idPlantilla);

	CarpetaPlantillaModel obtenerArchivosVerificacion();

	CarpetaPlantillaModel obtenerArchivosReintegros();
	
}
