package com.sisecofi.admindevengados.controller.descuentos_deducciones_penalizaciones;

import java.util.List;
import java.util.Map;

import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import com.sisecofi.libreria.comunes.service.PathService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.CancelarDictamenDto;
import com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto.*;
import com.sisecofi.admindevengados.repository.descuentos_deduccione_penalizaciones.CatTipoDescuentoRepository;
import com.sisecofi.admindevengados.service.descuentos_deducciones_penalizaciones.DeduccionesDescuentosPenalizacionesService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDescuento;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DescuentosDPCtrl {

	private final CatTipoDescuentoRepository catTipoDescuentoRepository;
	private final DeduccionesDescuentosPenalizacionesService deduccionesDescuentosPenalizacionesService;
	private final PathService pathService;

	// catalogo de deducciones

	@GetMapping("/proforma/ddp/tipo-descuento")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "||  @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolVerificadorGeneral() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto()")
	public ResponseEntity<List<CatTipoDescuentosDto>> obtenerTiposDescuentos() {
		List<CatTipoDescuento> tipodescuento = catTipoDescuentoRepository.findByEstatusTrue();

		List<CatTipoDescuentosDto> tipoDescuentoDto = tipodescuento.stream()
				.map(tipo -> new CatTipoDescuentosDto(tipo.getIdTipoDescuento(), tipo.getNombre()))
				.toList();

		return ResponseEntity.ok(tipoDescuentoDto);

	}

	// alta deduccion, descuento, penalizacion
	@PostMapping("/proforma/ddp/crear-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<DeduccionesDPDto>> crearDeduDescPenalizacion(
			@RequestBody List<DeduccionesDPDto> deduccionesDtoList) {
		List<DeduccionesDPDto> responseList = deduccionesDescuentosPenalizacionesService.crearDdp(deduccionesDtoList);
		return ResponseEntity.ok(responseList);

	}

	@PutMapping("/proforma/ddp/modificar-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<DeduccionesDPModiDto>> modificarDdp(
			@RequestBody List<DeduccionesDPModiDto> deduccionesDtoList) {

		List<DeduccionesDPModiDto> responseList = deduccionesDescuentosPenalizacionesService
				.modificarDdp(deduccionesDtoList);
		return ResponseEntity.ok(responseList);

	}

	@DeleteMapping("/proforma/ddp/eliminar-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Void> eliminarDdp(@RequestBody List<Long> idDdp) {
		deduccionesDescuentosPenalizacionesService.eliminacionLogicaDdp(idDdp);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/proforma/ddp/buscar-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato() "
			+ "|| @seguridad.validarRolVerificadorGeneral() " + "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen()")
	public ResponseEntity<List<DeduccionesDpConsultaDto>> buscarPorIdDictamen(
			 @RequestParam long idDictamen) {
		
	List<DeduccionesDpConsultaDto> resultado= deduccionesDescuentosPenalizacionesService.buscarPorIdDictamen(idDictamen);
	return ResponseEntity.ok(resultado);			
		
	}

	@PostMapping("/proforma/ddp/buscar-moneda")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() " + "|| @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<List<MonedaResponseDto>> buscarMonedasPorIdDictamen(
			@RequestBody Map<String, Long> request) {
		Long dictamenId = request.get("idDictamen");
		List<MonedaResponseDto> resultado = deduccionesDescuentosPenalizacionesService
				.buscarMonedasPorIdDictamen(dictamenId);
		return ResponseEntity.ok(resultado);

	}

	//
	@PutMapping("/proforma/ddp/examinar-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ArchivoPlantillaDictamenModel> cargarArchivoPenasDeducciones(
			@RequestParam(value = "detallePenasDeducciones", required = false) MultipartFile detallePenasDeducciones,
			@RequestParam("idDictamen") Long idDictamen) {

		if (!pathService.comprobarArchivo(detallePenasDeducciones)) {
            return ResponseEntity.badRequest().body(null);
        }
		ArchivoPlantillaDictamenModel archiPlan = deduccionesDescuentosPenalizacionesService
				.procesarArchivoPenasDeducciones(detallePenasDeducciones, idDictamen);

		return ResponseEntity.ok(archiPlan);
	}

	@PutMapping("/proforma/ddp/descargar-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<String> descargarDdpPath(@RequestParam("path") String path,
			@RequestParam Long dictamenId) {
		return new ResponseEntity<>(deduccionesDescuentosPenalizacionesService.descargarArchivo(path, dictamenId),
				HttpStatus.OK);
	}

	@PostMapping("/proforma/ddp/obtener-archivo-ddp")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolVerificadorEspecificoContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() " + "|| @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ArchivoDto> buscarRuta(@RequestParam Long idDictamen) {
		ArchivoDto resultado = deduccionesDescuentosPenalizacionesService.obtenerArchivoPorIdDictamen(idDictamen);
		return ResponseEntity.ok(resultado);
	}

	@PutMapping("/proforma/ddp/rechazar-proforma")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public boolean rechazarProforma(@RequestBody CancelarDictamenDto cancelarDictamenDto) {
		return deduccionesDescuentosPenalizacionesService.rechazarProforma(cancelarDictamenDto.getDictamenId().getIdDictamen(),
				cancelarDictamenDto.getDescripcion());
	}

	@PutMapping("/proforma/ddp/validar-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public Boolean validarDictamen(@RequestBody DictamenId idDictamen) {
		return deduccionesDescuentosPenalizacionesService.validarDictamen(idDictamen.getIdDictamen());

	}

	@GetMapping("/proforma/ddp/obtener-plantillas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<SubPlantilladorModel>> obtenerDatos() {
		return ResponseEntity.ok(deduccionesDescuentosPenalizacionesService.obtenerPlantillasProforma());
	}

	@PostMapping("/proforma/ddp/reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario() || "
			+ "@seguridad.validarRolParticipantesAdmonVerificacion() || "
			+ "@seguridad.validarRolVerificadorGeneral() || " + "@seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> validarDictamen(@RequestBody ProformaArchivoRequestDto idDictamen) {
		return ResponseEntity.ok(deduccionesDescuentosPenalizacionesService.obtenerArchivoProforma(idDictamen));
	}

}
