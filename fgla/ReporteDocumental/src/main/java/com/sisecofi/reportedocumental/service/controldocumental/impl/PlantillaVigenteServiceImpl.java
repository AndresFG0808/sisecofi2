package com.sisecofi.reportedocumental.service.controldocumental.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.reportecontroldoc.PlantillaVigenteDto;
import com.sisecofi.reportedocumental.repository.controldoc.PlantillaVigenteRepository;
import com.sisecofi.reportedocumental.service.controldocumental.PlantillaVigenteService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Service
@RequiredArgsConstructor
public class PlantillaVigenteServiceImpl implements PlantillaVigenteService {

	private final PlantillaVigenteRepository plantillaVigenteRepository;

	@Override
	public List<PlantillaVigenteDto> findByFaseProyecto(Integer idFaseProyecto) {
		return plantillaVigenteRepository.findByFaseProyecto(idFaseProyecto);
	}

}
