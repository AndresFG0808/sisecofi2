package com.sisecofi.reportedocumental.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.reportedocumental.microservicio.PistaMicroservicio;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.PistaException;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class PistaServicioImpl implements PistaService {

	private final PistaMicroservicio pistaMicroservicio;

	@Value("${ignorar.pistas}")
	private boolean pista;

	@Override
	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccionPista,
			String movimiento) {
		try {
			if (!pista) {
				PistaModel pistaModel = new PistaModel();
				pistaModel.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
				pistaModel.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
				pistaModel.setSeccionPistaModel(new CatSeccionPistaModel(idSeccionPista));
				pistaModel.setMovimiento(movimiento);
				pistaMicroservicio.guardarPista(pistaModel);
			}
			return true;
		} catch (Exception e) {
			throw new PistaException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

}
