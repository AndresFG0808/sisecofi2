package com.sisecofi.proyectos.controller;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.transaction.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sisecofi.proyectos.dto.FichaRequest;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.service.ServicioAlineacion;
import com.sisecofi.proyectos.service.ServicioFichaTecnica;
import com.sisecofi.proyectos.service.ServicioHistorico;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.interfaces.ValidacionCompleta;
import com.sisecofi.proyectos.util.interfaces.ValidacionIncompleta;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class FichaTecnicaCtrl {


	private final ServicioFichaTecnica fichaServicio;
	private final ServicioHistorico servicioHistorico;
	private final ServicioAlineacion servicioAlineacion;
	private final FichaTecnicaRepository fichaRepository;
 
	@GetMapping("/ficha/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolAdminMatrizDocumental() ")
	public ResponseEntity<FichaTecnicaResponse> obtenerFicha(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(fichaServicio.obtenerFicha(idProyecto), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/fichas-tecnicas") 
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<List<FichaTecnicaModel>> obtenerFichaId() {
		return new ResponseEntity<>(fichaRepository.findAll(), org.springframework.http.HttpStatus.OK);
	}

	@PostMapping("/modificar-ficha/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdminMatrizDocumental()")
	@Transactional
	public ResponseEntity<FichaTecnicaResponse> modificarFicha(@Validated({ValidacionCompleta.class, ValidacionIncompleta.class}) @RequestBody FichaRequest fichaRequest, @PathVariable Long idProyecto) {
		return new ResponseEntity<>(fichaServicio.guardarFicha(fichaRequest,idProyecto, true), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/usuarios")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<List<Usuario>> obtenerUsuarios() {
		return new ResponseEntity<>(fichaServicio.enlistarUsuarios(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/usuarios-organigrama")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<List<UsuarioInfoDto>> obtenerUsuariosOrganigrama() {
		return new ResponseEntity<>(fichaServicio.obtenerTodosLosUsuarios(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/reporte-lideres/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<String> obtenerReporteLideres(@PathVariable Long idProyecto){
		return new ResponseEntity<>(servicioHistorico.generarReporteHistorico(idProyecto), HttpStatus.OK);
	}
	
	@GetMapping("/reporte-alineacion/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolAdminMatrizDocumental()")
	public ResponseEntity<String> obtenerReporteAlineacion(@PathVariable Long idProyecto){
		return new ResponseEntity<>(servicioAlineacion.generarReporteAlineacion(idProyecto), HttpStatus.OK);
	}
}
