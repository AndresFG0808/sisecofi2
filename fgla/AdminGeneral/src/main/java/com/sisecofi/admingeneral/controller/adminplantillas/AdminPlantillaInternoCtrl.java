package com.sisecofi.admingeneral.controller.adminplantillas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.dto.adminplantillas.CarpetaDtoResponse;
import com.sisecofi.admingeneral.service.adminplantillas.AdminPlantillaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminPlantilla;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaCarpetasDto;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
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
public class AdminPlantillaInternoCtrl {

	@Qualifier("AdminPlantillaServiceLectura")
	@Autowired
	private AdminPlantillaService adminPlantillaServiceLectura;

	@SuppressWarnings("rawtypes")
	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/obtener-plantilla/{idPlantilla}")
	@ConsumoInterno
	public ResponseEntity<PlantillaDto<CarpetaDtoResponse>> obtenerPlantilla(
			@PathVariable("idPlantilla") Integer idPlantilla) {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerPlantilla(idPlantilla), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/plantillas")
	@ConsumoInterno
	public ResponseEntity<List<PlantillaVigenteModel>> plantillas() {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerPlantillas(), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/plantillas/{idFaseProyecto}")
	@ConsumoInterno
	public ResponseEntity<List<PlantillaVigenteModel>> plantillasFase(@PathVariable Integer idFaseProyecto) {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerPlantillasFase(idFaseProyecto), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/plantilla/{idPlantillaVigente}")
	@ConsumoInterno
	public ResponseEntity<PlantillaVigenteModel> obtenerPlantillaPorId(@PathVariable Integer idPlantillaVigente) {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenterPlantillaVigente(idPlantillaVigente),
				HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/obtener-plantilla-carpeta/{idPlantilla}")
	@ConsumoInterno
	public ResponseEntity<PlantillaCarpetasDto<CarpetaPlantillaModel>> obtenerPlantillaCarpeta(
			@PathVariable("idPlantilla") Integer idPlantilla) {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerPlantillaGeneral(idPlantilla), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/obtener-plantilla-verificacion")
	@ConsumoInterno
	public ResponseEntity<CarpetaPlantillaModel> obtenerArchivosVerificacion() {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerArchivosVerificacion(), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE_INTERNO + "/obtener-plantilla-reintegros")
	@ConsumoInterno
	public ResponseEntity<CarpetaPlantillaModel> obtenerArchivosReintegros() {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerArchivosReintegros(), HttpStatus.OK);
	}

}
