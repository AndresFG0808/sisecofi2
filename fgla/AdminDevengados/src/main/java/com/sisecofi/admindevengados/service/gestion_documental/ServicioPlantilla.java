package com.sisecofi.admindevengados.service.gestion_documental;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

@Service
public interface ServicioPlantilla {

	@SuppressWarnings("rawtypes")
	PlantillaDto obtenerPlantillaPorId(Integer idPlantilla);

	List<PlantillaVigenteModel> plantillasPorFase(Integer idFaseProyecto);

}
