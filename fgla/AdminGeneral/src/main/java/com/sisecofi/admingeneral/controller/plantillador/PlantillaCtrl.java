package com.sisecofi.admingeneral.controller.plantillador;

import java.util.List;

import com.sisecofi.admingeneral.dto.adminplantillas.ObtenerPlantillaDTO;
import com.sisecofi.admingeneral.dto.adminplantillas.CargaPlantillaDTO;
import com.sisecofi.admingeneral.service.plantillador.CargaPlantilla;
import com.sisecofi.admingeneral.util.ConstantesAdminPlantilla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sisecofi.admingeneral.dto.plantillador.ContenidoPlantillaDto;
import com.sisecofi.admingeneral.dto.plantillador.PlantilladorDto;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.libreria.comunes.model.plantillador.CatTipoPlantillador;
import com.sisecofi.libreria.comunes.service.PathService;
import com.sisecofi.admingeneral.service.plantillador.CatEstatusPlantillaService;
import com.sisecofi.admingeneral.service.plantillador.PlantillaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesPlantillador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class PlantillaCtrl {
	@Autowired
	private final PlantillaService plantillaService;
	@Autowired
	private final CatEstatusPlantillaService catEstatusPlantillaService;
	@Autowired
	private final PathService pathService;
	@Autowired
	private CargaPlantilla cargaPlantillaService;

	@GetMapping(ConstantesPlantillador.PATH_BASE + "/tipo-plantilla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatTipoPlantillador>> obtenerPlantillas() {
		return new ResponseEntity<>(catEstatusPlantillaService.obternerPlantillas(), HttpStatus.OK);
	}

	@GetMapping(ConstantesPlantillador.PATH_BASE + "/{idTipoPlantilla}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<PlantilladorDto> buscarPlantillas(
			@PathVariable("idTipoPlantilla") Integer idEstatusPlantilla) {
		return new ResponseEntity<>(plantillaService.buscarPlantillas(idEstatusPlantilla), HttpStatus.OK);
	}

	@GetMapping(ConstantesPlantillador.PATH_BASE)
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<PlantilladorDto>> buscarPlantillas() {
		return new ResponseEntity<>(plantillaService.buscarPlantillas(), HttpStatus.OK);
	}

	@PutMapping(ConstantesPlantillador.PATH_BASE + "/plantillas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> guardarPlantillas(@RequestBody List<PlantilladorDto> requestPlantilla) {
		return new ResponseEntity<>(plantillaService.guardarPlantillas(requestPlantilla), HttpStatus.OK);
	}

	@PutMapping(ConstantesPlantillador.PATH_BASE)
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> guardarPlantillas(@RequestBody RequestPlantilla requestPlantilla) {
		return new ResponseEntity<>(plantillaService.guardarPlantillas(requestPlantilla), HttpStatus.OK);
	}

	@PutMapping(ConstantesPlantillador.PATH_BASE + "/vista-previa")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> vistaPreviaPlantillas(@RequestBody RequestPlantilla requestPlantilla) {
		return new ResponseEntity<>(plantillaService.vistaPreviaPlantillas(requestPlantilla), HttpStatus.OK);
	}

	@PostMapping(ConstantesPlantillador.PATH_BASE + "/contenido-plantillador")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ContenidoPlantillaDto> obtenerContenidoPlantillador(@RequestBody PlantilladorDto dto) {
		return new ResponseEntity<>(
				plantillaService.obtenerContenidoPlantilladorDto(dto),
				HttpStatus.OK);
	}

	@PostMapping(ConstantesAdminPlantilla.PATH_BASE + "/cargar-excel")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<CargaPlantillaDTO> subirExcel(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name, @RequestParam("id") Long id) {
		
		if (!pathService.comprobarArchivo(file)) {
            return ResponseEntity.badRequest().body(null);
        }
		CargaPlantillaDTO response = cargaPlantillaService.cargaPlantilla(file, name, id);
		return ResponseEntity.ok(response);
	}

	@PostMapping(ConstantesPlantillador.PATH_BASE + "/plantillas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<Boolean> guardarPlantillasNuevas(@RequestBody List<PlantilladorDto> request) {
		return ResponseEntity.ok(plantillaService.guardarNuevasPlantillas(request));
	}

	@PostMapping(ConstantesPlantillador.PATH_BASE + "/descarga-plantilla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ObtenerPlantillaDTO> descargaPlantilla(@RequestParam String path) {
		return ResponseEntity.ok(cargaPlantillaService.obtenerPlantilla(path));

	}
	
	@PutMapping(ConstantesPlantillador.PATH_BASE + "/plantillas/modificar-contenido")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ContenidoPlantillaDto> guardarInformacionPlantilla(@RequestBody ContenidoPlantillaDto dto) {
		return new ResponseEntity<>(plantillaService.guardarInformacionPlantilla(dto), HttpStatus.OK);
	}
	
	@PostMapping(ConstantesPlantillador.PATH_BASE + "/plantillas/layout-ayuda")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> obtenerLayoutAyuda(@RequestBody ContenidoPlantillaDto dto) {
		return new ResponseEntity<>(plantillaService.obtenerVariablesAyuda(dto), HttpStatus.OK);
	}
	
	

}
