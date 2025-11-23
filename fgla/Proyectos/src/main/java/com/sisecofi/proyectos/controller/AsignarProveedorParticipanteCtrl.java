package com.sisecofi.proyectos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.RequestMultiple;
import com.sisecofi.proyectos.dto.CatalogoProveedorDto;
import com.sisecofi.proyectos.dto.ProyectoProveedorRequestDto;
import com.sisecofi.proyectos.dto.ProyectoProveedorResponseDto;
import com.sisecofi.proyectos.microservicio.ProveedorMicroservicio;
import com.sisecofi.proyectos.service.ProyectoProveedorService;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class AsignarProveedorParticipanteCtrl {

	private final ProyectoProveedorService proyectoProveedorService;
	private final ProveedorMicroservicio proveedorMicroservicio;

	@GetMapping("/proveedores-activos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<List<CatalogoProveedorDto>> obtenerTodoslosProveedores() {
		return new ResponseEntity<>(proveedorMicroservicio.obtenerTodoslosProveedores(), HttpStatus.OK);
	}

	@GetMapping("/proveedores-asignados/proyecto/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<List<ProyectoProveedorResponseDto>> obtenerProveedoresAsignados(
			@PathVariable Long idProyecto) {
		return new ResponseEntity<>(proyectoProveedorService.getProveedoresAsignados(idProyecto), HttpStatus.OK);
	}

	@PutMapping("/proveedores-asignados/guardar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonVerificacion()")
	public ResponseEntity<List<ProyectoProveedorResponseDto>> guardarProveedoresAsignados(
			@Valid @RequestBody @NotEmpty(message = ErroresEnum.MensajeValidation.LONGITUD_LISTA_PROVEEDOR) RequestMultiple<ProyectoProveedorRequestDto, Long> lista) {
		return new ResponseEntity<>(proyectoProveedorService.guardarProveedoresAsignados(lista), HttpStatus.CREATED);
	}

	@DeleteMapping("/proveedores-asignados/eliminar-proveedor/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonVerificacion()")
	public ResponseEntity<Boolean> eliminarProveedorAsignado(@PathVariable Long id) {
		return new ResponseEntity<>(proyectoProveedorService.eliminarProveedorAsignado(id), HttpStatus.OK);
	}

}
