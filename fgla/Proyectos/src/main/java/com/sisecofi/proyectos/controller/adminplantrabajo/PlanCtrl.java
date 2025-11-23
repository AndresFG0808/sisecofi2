package com.sisecofi.proyectos.controller.adminplantrabajo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.libreria.comunes.service.PathService;
import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionCompletadoRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionMultipleRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;
import com.sisecofi.proyectos.service.adminplantrabajo.ReportePlanTrabajoService;
import com.sisecofi.proyectos.service.adminplantrabajo.PlanTrabajoRespaldoService;
import com.sisecofi.proyectos.service.adminplantrabajo.CalculosMasivoService;
import com.sisecofi.proyectos.service.adminplantrabajo.CargaPlantilladorService;
import com.sisecofi.proyectos.service.adminplantrabajo.PlanService;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.adminplantrabajo.constantes.ConstantesPlanTrabajo;
import com.sisecofi.proyectos.util.enums.ErroresPlanTrabajoEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class PlanCtrl {

	private final PlanService planService;
	private final ReportePlanTrabajoService reportePlanTrabajoService;
	private final PlanTrabajoRespaldoService planTrabajoRespaldoService;
	private final CargaPlantilladorService cargaPlantilladorService;
	private final CalculosMasivoService calculoMasivoService;
	private final PathService pathService;

	@PutMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/cargar-guardar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<ListaTareas>> cargarPlan(@RequestParam("file") MultipartFile file,
			@RequestParam("idProyecto") Long idProyecto) throws IOException {
		if (!pathService.comprobarArchivo(file)) {
            return ResponseEntity.badRequest().body(null);
        }
		
		List<ListaTareas> jerarquiaTareas = planService.cargarPlan(file, idProyecto);
		return ResponseEntity.ok(jerarquiaTareas);
	}

	@GetMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/tabla-plan-trabajo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorGeneral()  || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Map<String, Object>> obtenerPlanTrabajo(@RequestParam Long idProyecto) {
		List<ListaTareas> listaTareas = planTrabajoRespaldoService.tablaPlanTrabajo(idProyecto);

		Map<String, Object> response = new HashMap<>();

		if (listaTareas == null || listaTareas.isEmpty()) {
			response.put("error", ErroresPlanTrabajoEnum.ERROR_NO_SE_ECONTRO.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		LocalDateTime ultimaModificacion = planTrabajoRespaldoService.obtenerUltimaModificacion(idProyecto);

		response.put("listaTareas", listaTareas);
		response.put("ultimaModificacion", ultimaModificacion);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/calcular-reales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario() || " +
			"@seguridad.validarRolApoyoAcppi() || " +
			"@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
			"@seguridad.validarRolLiderProyecto()")
	public ResponseEntity<List<TareaPlanTrabajoDto>> modificacionCampoTraeasOriginales(@RequestParam Long idProyecto,
			@RequestBody ModificacionMultipleRequest request) {
		List<TareaPlanTrabajoDto> tareasActualizadas = planTrabajoRespaldoService.modificarTareasOriginales(idProyecto,
				request.getModificaciones());

		return ResponseEntity.ok(tareasActualizadas);
	}

	@PostMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/calcular-completado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario() || " +
			"@seguridad.validarRolApoyoAcppi() || " +
			"@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
			"@seguridad.validarRolLiderProyecto()")
	public ResponseEntity<List<TareaPlanTrabajoDto>> calcularPorcentajeOriginal(
			@RequestParam Long idProyecto,
			@RequestParam(required = false) Integer idTarea,
			@RequestBody(required = false) List<ModificacionCompletadoRequest> modificaciones) {

		if (modificaciones == null) {
			modificaciones = new ArrayList<>();
		}

		// Obtien el valor de completado del requestBody si existe
		List<TareaPlanTrabajoDto> tareasActualizadas = planTrabajoRespaldoService
				.calcularPorcentajeListaTareas(idProyecto, modificaciones);
		return ResponseEntity.ok(tareasActualizadas);
	}

	@GetMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/generar-reporte")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario() || " +
			"@seguridad.validarRolApoyoAcppi() || " +
			"@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
			"@seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<byte[]> obtenerReportePlanTrabajo(@RequestParam("idProyecto") Long idProyecto) {
		byte[] contenido = reportePlanTrabajoService.obtenerReportePlanTrabajo(idProyecto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "Plan Trabajo.xlsx");

		return ResponseEntity.ok().headers(headers).body(contenido);
		//
	}

	@GetMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/consultar-plantilla")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorGeneral()  || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<Map<String, Object>>> consultarPlantillas(
			@RequestParam(required = false) String nombrePlantilla) {
		List<Map<String, Object>> plantillas = cargaPlantilladorService.obtenerPlantillas(nombrePlantilla);
		return ResponseEntity.ok(plantillas);
	}

	@PostMapping("/" + ConstantesPlanTrabajo.PATH_BASE + "/calculo-masivo")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || " +
			"@seguridad.validarRolAdminSistemaSecundario()")
	public ResponseEntity<List<TareaPlanTrabajoDto>> calcularPorcentajePlaneadoMasivo() {
		List<TareaPlanTrabajoDto> resultado = calculoMasivoService.calculosMasivos();
		return ResponseEntity.ok(resultado);
	}

}