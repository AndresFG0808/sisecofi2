package com.sisecofi.admindevengados.controller;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.IdSoporteDocumentalDto;
import com.sisecofi.admindevengados.dto.PistaSoporteDocumentalDto;
import com.sisecofi.admindevengados.dto.SoporteDocumentalDto;
import com.sisecofi.admindevengados.model.SoporteDocumentalModel;
import com.sisecofi.admindevengados.service.SoporteDocumentalService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.service.PathService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class SoporteDocumentalController {

	private final SoporteDocumentalService soporteDocumentalService;
	private final PathService pathService;

	@PutMapping("/cargar-archivo-soporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
	public ResponseEntity<String> cargarArchivoFaseIndividual(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		
		if (!pathService.comprobarArchivo(file)) {
            return ResponseEntity.badRequest().body("Nombre de archivo o extensión no permitida.");
        }
		String value = soporteDocumentalService.cargarArchivo(0L, file);
    	String safeValue = StringEscapeUtils.escapeHtml4(value);
    	return new ResponseEntity<>(safeValue, HttpStatus.OK);
		
	}

	@PostMapping("/validar-existe-penas-deducciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "||  @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> validarSiExistenPenasDeducciones(@RequestBody DictamenId dictamenId) {
		return new ResponseEntity<>(
				soporteDocumentalService.validarSiExistenPenasDeducciones(dictamenId.getIdDictamen()), HttpStatus.OK);
	}

	@PutMapping("/registrar-actualizar-soporte-documental")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<SoporteDocumentalModel>> actualizarSoporteDocumental(
			@ModelAttribute SoporteDocumentalDto soporteDocumental,
			@RequestPart(value = "detallePenasDeducciones", required = false) MultipartFile detallePenasDeducciones) {
		if (!pathService.comprobarArchivo(detallePenasDeducciones)) {
            return ResponseEntity.badRequest().body(null);
        }
		SoporteDocumentalModel soporte = soporteDocumentalService.guardarOActualizarSoporteDocumental(soporteDocumental,
				detallePenasDeducciones);

		ResponseGeneric<SoporteDocumentalModel> data = ResponseGeneric.<SoporteDocumentalModel>builder().build();
		data.setData(soporte);
		data.setMsj(ErroresEnum.SOPORTE_DOCUMENTAL_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PutMapping("/actualizar-entregable-soporte-documental")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<SoporteDocumentalModel> actualizarEntregables(
			@ModelAttribute IdSoporteDocumentalDto idSoporteDocumental,
			@RequestPart(value = "entregables", required = false) MultipartFile entregables) {
		if (!pathService.comprobarArchivo(entregables)) {
            return ResponseEntity.badRequest().body(null);
        }
		
		return new ResponseEntity<>(soporteDocumentalService.actualizarEntregablese(idSoporteDocumental, entregables),
				HttpStatus.OK);
	}

	@PutMapping("/actualizar-oficio-soporte-documental")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<SoporteDocumentalModel> actualizarOficio(
			@ModelAttribute IdSoporteDocumentalDto idSoporteDocumental,
			@RequestPart(value = "oficio", required = false) MultipartFile oficio) {
		
		if (!pathService.comprobarArchivo(oficio)) {
            return ResponseEntity.badRequest().body(null);
        }
		return new ResponseEntity<>(soporteDocumentalService.actualizarOficio(idSoporteDocumental, oficio),
				HttpStatus.OK);
	}

	@PostMapping("/obtener-soporte-documental")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<SoporteDocumentalModel> obtenerSoporteDocumental(@RequestBody DictamenId dictamenId) {
		return new ResponseEntity<>(soporteDocumentalService.obtenerSoporteDocumental(dictamenId.getIdDictamen()),
				HttpStatus.OK);
	}

	@PutMapping("/descargar-archivo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<String> descargarFolderSatCloud(@RequestParam("path") String path,
			@RequestParam Long dictamenId) {
		return new ResponseEntity<>(soporteDocumentalService.descargarArchivo(path, dictamenId), HttpStatus.OK);

	}

	@PostMapping("/dictaminado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<Dictamen>> dictaminado(@RequestBody DictamenId idDictamen) {

		Dictamen dictamen = soporteDocumentalService.dictaminado(idDictamen.getIdDictamen() );
		ResponseGeneric<Dictamen> data = ResponseGeneric.<Dictamen>builder().build();
		data.setData(dictamen);
		data.setMsj(ErroresEnum.DICTAMEN_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);

	}	

	@GetMapping("/validar-responsabilidad/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<Boolean> validarResponsabilidad(@PathVariable Long idContrato) {
		return new ResponseEntity<>(soporteDocumentalService.validarResponsabilidad(idContrato), HttpStatus.OK);
	}

	@PostMapping("/registrar-pista-soporte-documental")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> crearPista(@RequestBody PistaSoporteDocumentalDto pista) {
		return new ResponseEntity<>(soporteDocumentalService.crearPista(pista.getIdSoporteDocumental(),
				pista.getIdDictamen()  , pista.getEstatusPeticiones()), HttpStatus.CREATED);
	}

	@PostMapping("/validar-responsabilidad-dictaminado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<Boolean> validarResponsabilidadSoporteDictaminado(@RequestBody DictamenId idDictamen) {
		return new ResponseEntity<>(soporteDocumentalService.validarResponsabilidadSoporteDictaminado(idDictamen.getIdDictamen()  ), HttpStatus.OK);
	}
	
	@PostMapping("/validar-fecha-recepcion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<Boolean> validarResponsabilidadFechaRecepcion(@RequestBody DictamenId idDictamen) {
		return new ResponseEntity<>(soporteDocumentalService.validarResponsabilidadFechaRecepcion(idDictamen.getIdDictamen() ), HttpStatus.OK);
	}

}
