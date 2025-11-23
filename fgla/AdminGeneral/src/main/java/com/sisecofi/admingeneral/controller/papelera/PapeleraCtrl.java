package com.sisecofi.admingeneral.controller.papelera;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.service.papelera.PapeleraService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesPapelara;
import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class PapeleraCtrl {

	private final PapeleraService papeleraService;

	@PutMapping(ConstantesPapelara.PATH_BASE_INTERNO + "/papelera/guardar-papelera")
	@ConsumoInterno
	public ResponseEntity<PapeleraModel> generarId(@RequestBody PapeleraModel model) {
		return new ResponseEntity<>(papeleraService.guardarPapelera(model), HttpStatus.CREATED);
	}

	@GetMapping(ConstantesPapelara.PATH_BASE_INTERNO + "/papelera/obtener-papelera")
	@ConsumoInterno
	public ResponseEntity<List<PapeleraModel>> obtenerPpelera() {
		return new ResponseEntity<>(papeleraService.obtenerPapelera(), HttpStatus.OK);
	}

	@GetMapping(ConstantesPapelara.PATH_BASE_INTERNO + "/papelera/obtener-archivo/{idPapelera}")
	@ConsumoInterno
	public ResponseEntity<PapeleraModel> obtenerArchivo(@PathVariable("idPapelera") Long idPapelera) {
		return new ResponseEntity<>(papeleraService.obtenerArchivoPapelera(idPapelera), HttpStatus.OK);
	}

	@GetMapping(ConstantesPapelara.PATH_BASE_INTERNO + "/papelera/restaurar-archivo/{idPapelera}")
	@ConsumoInterno
	public ResponseEntity<PapeleraModel> restaurarArchivo(@PathVariable("idPapelera") Long idPapelera) {
		return new ResponseEntity<>(papeleraService.restaurarArchivo(idPapelera), HttpStatus.OK);
	}
	
	@DeleteMapping(ConstantesPapelara.PATH_BASE_INTERNO + "/papelera/eliminar-archivo/{idPapelera}")
	@ConsumoInterno
	public ResponseEntity<PapeleraModel> eliminarArchivo(@PathVariable("idPapelera") Long idPapelera) {
		
		papeleraService.eliminarArchivoPapelera(idPapelera);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
