package com.sisecofi.admingeneral.controller.adminpistas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.service.adminpistas.PistaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesPistas;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
public class PistaInternoCtrl {

	private final PistaService pistaService;

	public PistaInternoCtrl(PistaService pistaService) {
		super();
		this.pistaService = pistaService;
	}

	@PutMapping(ConstantesPistas.PATH_BASE_INTERNO + "/guardar")
	@ConsumoInterno
	public ResponseEntity<PistaModel> guardarPista(@RequestBody PistaModel pistaModel) {
		return new ResponseEntity<>(pistaService.guardarPista(pistaModel), HttpStatus.CREATED);
	}
}
