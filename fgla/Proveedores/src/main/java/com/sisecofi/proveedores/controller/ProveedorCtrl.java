package com.sisecofi.proveedores.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.proveedores.dto.CatalogoProveedorDto;
import com.sisecofi.proveedores.dto.ConsultaProveedorDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;
import com.sisecofi.proveedores.dto.ProveedoGeneralDto;
import com.sisecofi.proveedores.dto.ProveedorDto;
import com.sisecofi.proveedores.dto.ProveedorRequestDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.proveedores.service.ProveedorService;
import com.sisecofi.proveedores.util.Constantes;

import java.util.List;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 * 
 * @author adtolentino
 * 
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class ProveedorCtrl {

	@Autowired
	private ProveedorService proveedorService;

	// Alta proveedor
	@PostMapping("/crear-proveedor")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ProveedorModel> crearProveedor(@Valid @RequestBody ProveedorDto proveedor) {
		return new ResponseEntity<>(proveedorService.crearProveedor(proveedor), HttpStatus.CREATED);
	}

	// Consulta general
	@PostMapping("/proveedores")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Page<ConsultaProveedorDto>> obtenerTodoslosProveedores(
			@RequestBody ProveedorRequestDto proveedorRequestDto) {
		Page<ConsultaProveedorDto> proveedores = proveedorService.obtenerTodosLosProveedores(proveedorRequestDto);
		return ResponseEntity.ok(proveedores);
	}

	// Consulta proveedor por Id
	@GetMapping("/proveedor/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ConsultaProveedorDto> obtenerProveedorPorId(@PathVariable Long id) {
		ConsultaProveedorDto proveedorDto = proveedorService.obtenerProveedorPorId(id);
		return ResponseEntity.ok().body(proveedorDto);
	}
	
	@PostMapping("/proveedor-validar-cumple")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<List<DictamenTecnicoResponseDto>> obtenerProveedorPorId(@RequestBody ProveedorRequestDto filtro) {
		List<DictamenTecnicoResponseDto> proveedorDto = proveedorService.obtenerProveedorPorIdCumple(filtro);
		return ResponseEntity.ok().body(proveedorDto);
	}
	
	@PostMapping("/proveedor-titulos-general")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<List<TituloServicioResponseDto>> obtenerProveedorTitulosGeneral(@RequestBody ProveedorRequestDto filtro) {
		List<TituloServicioResponseDto> proveedorDto = proveedorService.obtenerProveedorTitulosGeneral(filtro);
		return ResponseEntity.ok().body(proveedorDto);
	}

	// Modifica proveedor
	@PutMapping("/proveedor/{id}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<ProveedorDto> actualizarProveedor(@PathVariable Long id,
			@RequestBody ProveedorDto proveedorDto) {
		ProveedorDto proveedorActualizadoDto = proveedorService.actualizarProveedor(id, proveedorDto);
		return ResponseEntity.ok(proveedorActualizadoDto);

	}

	// Lista Proveedores
	@GetMapping("/catalogo-proveedores")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public List<CatalogoProveedorDto> listarProveedores() {
		return proveedorService.listarProveedores();
	}

	// Consulta proveedores activo
	@GetMapping("/catalogo-proveedores-activos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public List<CatalogoProveedorDto> listarProveedoresActivos() {
		return proveedorService.listarProveedoresActivos();
	}

	// Consulta general
	@PostMapping("/proveedores-general")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||" + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolApoyoAcppi() || " + "@seguridad.validarRolGestorTitutulos() || "
			+ "@seguridad.validarRolUsuarioConsulta() || " + "@seguridad.validarRolLiderProyecto() || "
			+ "@seguridad.validarRolAdministradorContrato() || " + "@seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Page<ProveedoGeneralDto>> obtenerListaGeneralProveedores(
			@RequestBody ProveedorRequestDto proveedorRequestDto) {
		Page<ProveedoGeneralDto> provedoresGeneral = proveedorService.filtrarProveedores(proveedorRequestDto);
		return ResponseEntity.ok(provedoresGeneral);

	}

	@GetMapping("/prueba-version-integracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> obtenerVersion() {
		return new ResponseEntity<>("Version 03/03/2025", HttpStatus.OK);
	}
}
