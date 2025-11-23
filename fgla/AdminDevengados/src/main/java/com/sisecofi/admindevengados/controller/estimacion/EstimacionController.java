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

import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import com.sisecofi.admindevengados.dto.EstimacionResponse;
import com.sisecofi.admindevengados.microservicio.ContratoMicoservicio;
import com.sisecofi.admindevengados.model.ServicioEstimadoModel;
import com.sisecofi.admindevengados.service.estimacion.EstimacionService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoConvenioModDto;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class EstimacionController {

	private final EstimacionService estimacionService;
	private final ContratoMicoservicio contratoMicoservicio;

	@PostMapping("/crear-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> crearEstimacion(@Valid @RequestBody EstimacionModel estimacion) {
		return new ResponseEntity<>(estimacionService.crearEstimacion(estimacion), HttpStatus.OK);
	}
	
	@GetMapping("/prueba/{nombreCorto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<ContratoConvenioModDto> obtenerContratoNombreCorto(@PathVariable("nombreCorto") String nombreCorto) {
		return new ResponseEntity<>(contratoMicoservicio.obtenerContratoNombreCorto(nombreCorto), HttpStatus.OK);
	}

	@GetMapping("/estimacion/dictamenes-relacionados")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<List<DevengadoBusquedaResponse>> obtenerDictamenesAsociadosRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.obtenerDictamenesAsociados(id), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/estimacion/dictamenes-relacionados-exportar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<byte[]> exportarExcelDictamenesAsociadosRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.exportarExcelDictamenesAsociados(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/obtener-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> obtenerEstimacionRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.obtenerEstimacion(id), HttpStatus.OK);
	}
	
	@PutMapping("/modificar-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> modificarEstimacion(@Valid @RequestBody EstimacionModel estimacion) {
		return new ResponseEntity<>(estimacionService.modificarEstimacion(estimacion), HttpStatus.OK);
	}
	
	
	@GetMapping("/duplicar-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> duplicarEstimacionRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.duplicarEstimacion(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/estimacion/facturas-relacionadas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<List<FacturaContratoDto>> obtenerFacturasAsociadasRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.obtenerFacturasAsociadas(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/estimacion/facturas-relacionadas-exportar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<byte[]> exportarFacturasAsociadasRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.exportarFacturasAsociadas(id), HttpStatus.OK);
	}
	
	
	@PutMapping("/cambiar-a-inicial")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> cambiarAInicialRegex(@RequestParam String id) {
		return new ResponseEntity<>(estimacionService.cambiarAInicial(id), HttpStatus.OK);
	}
	
	
	@PutMapping("/cancelar-estimacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> cancelarEstimacionRegex(@RequestBody String justificacion, @RequestParam String id) {
		return new ResponseEntity<>(estimacionService.cancelarEstimacion(id, justificacion), HttpStatus.OK);
	}
	
	@PostMapping("/calcular-estimado-total")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<EstimacionResponse> calcularMontosEstimados(@RequestBody List<ServicioEstimadoModel> servicios) {
		return new ResponseEntity<>(estimacionService.obtenerEstimacionPersist(servicios), HttpStatus.OK);
	}
}
