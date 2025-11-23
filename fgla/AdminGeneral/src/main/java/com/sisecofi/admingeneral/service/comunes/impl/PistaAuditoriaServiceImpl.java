package com.sisecofi.admingeneral.service.comunes.impl;

import org.springframework.stereotype.Service;

import com.sisecofi.admingeneral.service.adminpistas.PistaService;
import com.sisecofi.admingeneral.service.comunes.PistaAuditoriaService;
import com.sisecofi.admingeneral.util.enums.ErroresPistasEnum;
import com.sisecofi.admingeneral.util.exception.PistaException;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class PistaAuditoriaServiceImpl implements PistaAuditoriaService {

	private final PistaService pistaService;

	@Override
	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, String movimiento) {
		try {
			PistaModel pista = new PistaModel();
			pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
			pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
			pista.setMovimiento(movimiento);
			pistaService.guardarPista(pista);
			return true;
		} catch (Exception e) {
			throw new PistaException(ErroresPistasEnum.ERROR_GUARDAR_PISTA);
		}
	}

}
