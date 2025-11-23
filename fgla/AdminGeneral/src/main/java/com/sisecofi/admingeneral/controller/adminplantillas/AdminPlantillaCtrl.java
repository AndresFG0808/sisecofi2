package com.sisecofi.admingeneral.controller.adminplantillas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sisecofi.admingeneral.dto.adminplantillas.CarpetaDtoResponse;
import com.sisecofi.admingeneral.dto.adminplantillas.PlantillaResponseDto;
import com.sisecofi.admingeneral.service.adminplantillas.AdminPlantillaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesAdminPlantilla;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.service.PathService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class AdminPlantillaCtrl {

	@Qualifier("AdminPlantillaServiceLectura")
	@Autowired
	private AdminPlantillaService adminPlantillaServiceLectura;
	@Autowired
	private final PathService pathService;

	@SuppressWarnings("rawtypes")
	@PutMapping(ConstantesAdminPlantilla.PATH_BASE + "/vista-previa-plantilla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<PlantillaDto<CarpetaDtoResponse>> vistaPreviaPlantilla(
			@RequestParam("file") MultipartFile file, @RequestParam("idFase") int idFase) {
		
		if (!pathService.comprobarArchivo(file)) {
            return ResponseEntity.badRequest().body(null);
        }
		return new ResponseEntity<>(adminPlantillaServiceLectura.lecturaPlantilla(file, idFase), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(ConstantesAdminPlantilla.PATH_BASE + "/cargar-plantilla/{idFase}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<PlantillaResponseDto> cargarPlantilla(@PathVariable("idFase") int idFase,
			@RequestBody PlantillaDto<CarpetaDtoResponse> plantilla) throws JsonProcessingException {
		return new ResponseEntity<>(adminPlantillaServiceLectura.guardarPlantilla(plantilla), HttpStatus.OK);
	}

	@PostMapping(ConstantesAdminPlantilla.PATH_BASE + "/modificar-estatus-plantilla/{idPlantillaVigente}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<PlantillaVigenteModel> modificarEPlantilla(
			@PathVariable("idPlantillaVigente") Integer idPlantillaVigente, @RequestBody boolean estatus) {
		return new ResponseEntity<>(adminPlantillaServiceLectura.guardarEstatusPlantilla(estatus, idPlantillaVigente),
				HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(ConstantesAdminPlantilla.PATH_BASE + "/obtener-plantilla/{idPlantilla}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<PlantillaDto<CarpetaDtoResponse>> obtenerPlantilla(
			@PathVariable("idPlantilla") Integer idPlantilla) {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerPlantilla(idPlantilla), HttpStatus.OK);
	}

	@GetMapping(ConstantesAdminPlantilla.PATH_BASE + "/plantillas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<List<PlantillaVigenteModel>> plantillas() {
		return new ResponseEntity<>(adminPlantillaServiceLectura.obtenerPlantillas(), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(ConstantesAdminPlantilla.PATH_BASE + "/actualizar-plantilla/{idPlantillaVigente}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() ||  @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<PlantillaVigenteModel> actualizarPlantilla(
			@PathVariable("idPlantillaVigente") int idPlantillaVigente,
			@RequestBody PlantillaDto<CarpetaDtoResponse> plantilla) throws JsonProcessingException {
		return new ResponseEntity<>(adminPlantillaServiceLectura.actualizarPlantilla(plantilla, idPlantillaVigente),
				HttpStatus.OK);
	}
	
	@GetMapping("/prueba-version-integracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> obtenerVersion() {
		return new ResponseEntity<>("Version 18/06/2025", HttpStatus.OK);
	}

}
