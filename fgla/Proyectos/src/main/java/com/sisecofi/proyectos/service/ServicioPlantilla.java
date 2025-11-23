package com.sisecofi.proyectos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.proyectos.dto.PlantillaVigenteModelDto;

@Service
public interface ServicioPlantilla {

	@SuppressWarnings("rawtypes")
	PlantillaDto obtenerPlantillaPorId(Integer idPlantilla);

	List<PlantillaVigenteModel> obtenerPlantillas();

	List<PlantillaVigenteModel> plantillasPorFase(Integer idFaseProyecto);

	PlantillaVigenteModelDto obtenerEstructura(Integer idComiteProyecto);

    PlantillaVigenteModelDto obtenerEstructuraPorIdPlantilla(Integer idPlantillaVigente);

}
