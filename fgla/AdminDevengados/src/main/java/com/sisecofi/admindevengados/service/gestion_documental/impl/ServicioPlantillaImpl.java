package com.sisecofi.admindevengados.service.gestion_documental.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.admindevengados.microservicio.PlantillaDocMicroservicio;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.gestion_documental.ServicioPlantilla;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class ServicioPlantillaImpl implements ServicioPlantilla {

	private final PlantillaDocMicroservicio plantillaDocMicroservicio;
	private final PistaService pistaService;

	@SuppressWarnings("rawtypes")
	@Override
	public PlantillaDto obtenerPlantillaPorId(Integer idPlantilla) {

		try {
			pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),
					TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

			return plantillaDocMicroservicio.obtenerPlantilla(idPlantilla);

		} catch (Exception e) {
			
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}


	@Override
	public List<PlantillaVigenteModel> plantillasPorFase(Integer idFaseProyecto) {
		try {
			return plantillaDocMicroservicio.plantillasFase(idFaseProyecto);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	
}
