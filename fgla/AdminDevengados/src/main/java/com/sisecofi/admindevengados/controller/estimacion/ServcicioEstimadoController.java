package com.sisecofi.admindevengados.controller.estimacion;

import java.util.List;

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

import com.sisecofi.admindevengados.dto.ServicioEstimacionDtoMod;
import com.sisecofi.admindevengados.dto.estimacion.ConveniosResponse;
import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.admindevengados.service.estimacion.EstimacionService;
import com.sisecofi.admindevengados.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class ServcicioEstimadoController {

	private final EstimacionService estimacionService;

	
	@GetMapping("/servicios-estimados")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<List<ServicioEstimadoModel>> obtenerServiciosEstimadosRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.obtenerServiciosEstimados(id, null), HttpStatus.OK);
	}
	

	@PostMapping("/servicios-estimados-calcular")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<List<ServicioEstimadoModel>> calcularServicio(@RequestBody List<ServicioEstimadoModel> servicios) {
		return new ResponseEntity<>(estimacionService.calcularServicio(servicios, null), HttpStatus.OK);
	}
	
	@PutMapping("/servicios-estimados-guardar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<ServicioEstimacionDtoMod> guardarServicios(@RequestParam String id, @RequestBody List<ServicioEstimadoModel> servicio) {
		return new ResponseEntity<>(estimacionService.guardarServicios(servicio, id, false), HttpStatus.OK);
	}
	

	@PutMapping("/servicios-estimados/volumetria")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<ServicioEstimacionDtoMod> volumetriaEstimadaRegex(@RequestParam String id, @RequestBody List<ServicioEstimadoModel> servicio) {
		return new ResponseEntity<>(estimacionService.volumetriaEstimada(servicio, id), HttpStatus.OK);
	}
	
	
	@GetMapping("/exportar-servicios-estimados")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<byte[]> exportarExcelServiciosRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.exportarExcelServicios(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/servicios-estimados/obtener-convenios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<ConveniosResponse> obtenerConveniosRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.obtenerConvenios(id), HttpStatus.OK);
	}

	@PutMapping("/servicios-estimados/cambiar-precio-unitario/{idEstimacion}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<List<ServicioEstimadoModel>> cambiarPrecioUnitario(@RequestBody String numeroConvenio, @PathVariable("idEstimacion") String idEstimacion) {
		return new ResponseEntity<>(estimacionService.cambiarPrecioUnitario(idEstimacion, numeroConvenio), HttpStatus.OK);
	}
	
	//eliminar
	@PutMapping("/servicios-estimados/cambiar-precio-unitario")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<List<ServicioEstimadoModel>> cambiarPrecioUnitarioRegex(@RequestParam String id,@RequestBody String numeroConvenio) {
		return new ResponseEntity<>(estimacionService.cambiarPrecioUnitario(id, numeroConvenio), HttpStatus.OK);
	}
}
