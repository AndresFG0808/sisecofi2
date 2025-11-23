package com.sisecofi.catalogos.service;

import java.util.Optional;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PistaService {

	boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento,
			Optional<Object> obj);

}
