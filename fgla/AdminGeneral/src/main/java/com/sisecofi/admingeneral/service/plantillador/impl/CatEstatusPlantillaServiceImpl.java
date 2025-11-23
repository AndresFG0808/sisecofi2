package com.sisecofi.admingeneral.service.plantillador.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.plantillador.CatTipoPlantillador;
import com.sisecofi.admingeneral.repository.plantillador.CatTipoPlantillaRepository;
import com.sisecofi.admingeneral.service.plantillador.CatEstatusPlantillaService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class CatEstatusPlantillaServiceImpl implements CatEstatusPlantillaService {

	private final CatTipoPlantillaRepository catEstatusPlantillaRepository;

	@Override
	public List<CatTipoPlantillador> obternerPlantillas() {
		return catEstatusPlantillaRepository.findAll();
	}

}
