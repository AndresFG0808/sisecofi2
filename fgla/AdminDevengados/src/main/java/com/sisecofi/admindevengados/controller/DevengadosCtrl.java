package com.sisecofi.admindevengados.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoProveedorDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.dto.DevengadoRequest;
import com.sisecofi.admindevengados.dto.EstimacionNuevaDto;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.admindevengados.service.DevengadoService;
import com.sisecofi.admindevengados.util.Constantes;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DevengadosCtrl {

	private final DevengadoService devengadoService;
	private final ContratoMicoservicio contratoMicoservicio;

	@GetMapping("/estatus-dictamen-estimacion/{tipoConsumo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerEstatus(@PathVariable String tipoConsumo) {
		return new ResponseEntity<>(devengadoService.obtenerEstatus(tipoConsumo), HttpStatus.OK);
	}

	@GetMapping("/contratos-vigentes/{vigente}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ContratoSimpleDto>> obtenerContratosVigentes(@PathVariable String vigente) {
		return new ResponseEntity<>(devengadoService.obtenerContratosVigentes(vigente), HttpStatus.OK);
	}

	@GetMapping("/proveedores-contrato/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ProveedorDto>> obtenerProveedoresContrato(@PathVariable Long idContrato) {
		return new ResponseEntity<>(devengadoService.obtenerProveedoresContrato(idContrato), HttpStatus.OK);
	}

	@PostMapping("/busqueda-dictamen-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"||  @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<DevengadoBusquedaResponse>> obtenerDictamenesEstimaciones(
			@RequestBody DevengadoRequest request) {
		return new ResponseEntity<>(devengadoService.obtenerDictamenesEstimaciones(request), HttpStatus.OK);
	}

	@GetMapping("/contrato-proveedor/{idContrato}/{idProveedor}")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<ContratoProveedorDto> obtenerProveedoresContrato(@PathVariable("idContrato") Long idContrato,
			@PathVariable("idProveedor") Long idProveedor) {
		return new ResponseEntity<>(contratoMicoservicio.obtenerContratoProveedor(idContrato, idProveedor),
				HttpStatus.OK);
	}

	@PostMapping("/exportar-dictamen-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"||  @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
	public ResponseEntity<String> exportarExcel(@RequestBody DevengadoRequest request) {
		return new ResponseEntity<>(devengadoService.exportarExcel(request), HttpStatus.OK);
	}

	@GetMapping("/crear-dictamen-estimacion/{idContrato}/{idProveedor}/{tipoConsumo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<EstimacionNuevaDto> nuevaEstimacion(@PathVariable("idContrato") Long idContrato,
			@PathVariable("idProveedor") Long idProveedor, @PathVariable("tipoConsumo") String tipoConsumo) {
		return new ResponseEntity<>(devengadoService.nuevaEstimacion(idContrato, idProveedor, tipoConsumo),
				HttpStatus.OK);
	}
	
	@GetMapping("/comprobar-dependencias/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> comprobarDependencias(@PathVariable("idContrato") Long idContrato) {
		return new ResponseEntity<> (devengadoService.comprobarDependencias(idContrato), HttpStatus.OK);
	}
	
	@GetMapping("/prueba-version-integracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> obtenerVersion() {
		return new ResponseEntity<>("Version 26/06/2025", HttpStatus.OK);
	}
}
