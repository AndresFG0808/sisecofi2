package com.sisecofi.admingeneral.service.adminpistas;

import java.util.Optional;

import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.service.CrudSevice;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PistaService extends CrudSevice<PistaModel> {

	PistaModel guardarPista(PistaModel model);

	boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento,
			Optional<Object> obj);

	boolean guardarPistaSimple(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento,
			Optional<Object> obj);

}
