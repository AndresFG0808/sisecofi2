package com.sisecofi.reportedocumental.controller.pistas;

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

import com.sisecofi.libreria.comunes.dto.reportedinamico.EmpleadoDto;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.reportedocumental.dto.pistareporte.BusquedaPistaDto;
import com.sisecofi.reportedocumental.dto.pistareporte.HistoricoPistaDto;
import com.sisecofi.reportedocumental.dto.pistareporte.PagePista;
import com.sisecofi.reportedocumental.service.pista.PistaReporteService;
import com.sisecofi.reportedocumental.util.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class PistaReporteCtrl {

	private final PistaReporteService pistaReporteService;

	@PostMapping("/pistas/reporte-pistas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<PagePista> obtenerReporte(@Valid @RequestBody BusquedaPistaDto busquedaDto) {
		return new ResponseEntity<>(pistaReporteService.obtenerReporte(busquedaDto), HttpStatus.OK);
	}

	@PostMapping("/pistas/reporte-pistas-export")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<byte[]> obtenerReporteExport(@RequestBody BusquedaPistaDto busquedaDto) {
		return new ResponseEntity<>(pistaReporteService.obtenerReporteExport(busquedaDto), HttpStatus.OK);
	}

	@GetMapping("/pistas/usuarios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<EmpleadoDto>> obtenerUsuarios() {
		return new ResponseEntity<>(pistaReporteService.obtenerUsuarios(), HttpStatus.OK);
	}

	@GetMapping("/pistas/modulo-pista")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatModuloPistaModel>> obtenerModuloPista() {
		return new ResponseEntity<>(pistaReporteService.obtenerModuloPista(), HttpStatus.OK);
	}

	@GetMapping("/pistas/seccion-pista/{idModulo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatSeccionPistaModel>> obtenerSeccionPista(@PathVariable("idModulo") Integer idModulo) {
		return new ResponseEntity<>(pistaReporteService.obtenerSeccionPista(idModulo), HttpStatus.OK);
	}

	@GetMapping("/pistas/movimiento-pista")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<CatTipoMovPistaModel>> obtenerTipoMovimientoPista() {
		return new ResponseEntity<>(pistaReporteService.obtenerTipoMovimientoPista(), HttpStatus.OK);
	}

	@GetMapping("/pistas/pista/{idPista}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<HistoricoPistaDto> obtenerTipoMovimientoPista(@PathVariable("idPista") Long idPista) {
		return new ResponseEntity<>(pistaReporteService.buscarPistas(idPista), HttpStatus.OK);
	}
}
