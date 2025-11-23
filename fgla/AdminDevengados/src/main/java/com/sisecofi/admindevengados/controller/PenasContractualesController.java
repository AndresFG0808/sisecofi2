package com.sisecofi.admindevengados.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import com.sisecofi.admindevengados.service.PenasContractualesService;
import com.sisecofi.admindevengados.service.ServicioReportePenasContractuales;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/" + Constantes.PATH_BASE)
public class PenasContractualesController {

	private final PenasContractualesService penaContractualService;
	private final ServicioReportePenasContractuales servicioReportePenasContractuales;

	@PostMapping("/penas-contractuales-guardar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<PenasContractualesModel>>> guardarDictamen(
			@RequestBody @Valid List<ObtenerPenaContractualDto> penas) {

		List<PenasContractualesModel> penass = penaContractualService.guardarPenaContractual(penas);

		ResponseGeneric<List<PenasContractualesModel>> data = ResponseGeneric.<List<PenasContractualesModel>>builder()
				.build();
		data.setData(penass);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PutMapping("/penas-contractuales-modificar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<PenasContractualesModel>>> modificarPenaContractual(
			@RequestBody @Valid List<ObtenerPenaContractualDto> penas) {

		List<PenasContractualesModel> penass = penaContractualService.modificarPenaContractual(penas);

		ResponseGeneric<List<PenasContractualesModel>> data = ResponseGeneric.<List<PenasContractualesModel>>builder()
				.build();
		data.setData(penass);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/penas-contractuales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<ObtenerPenaContractualDto>>> obtenerPenasContractuales(
			@RequestBody DictamenId dictamenId) {

		List<ObtenerPenaContractualDto> penas = penaContractualService
				.obtenerPenasContractuales(dictamenId.getIdDictamen());

		ResponseGeneric<List<ObtenerPenaContractualDto>> data = ResponseGeneric
				.<List<ObtenerPenaContractualDto>>builder().build();
		data.setData(penas);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/generar-excel-penas-contractuales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<String>> generarExcelPenasContractuales(
			@RequestBody List<PenaContractualExcelDto> lista) {
		String base64 = servicioReportePenasContractuales.obtenerInformacion(lista);
		ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
		data.setData(base64);
		data.setMsj(ErroresEnum.DICTAMEN_EXPORTAR_EXCEL);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/obtener-documentos-pena-contractual/{idContrato}/{tipo}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<List<Object>> obtenerDocumentosContratoFuncional(@PathVariable Long idContrato,
			@PathVariable String tipo) {
		return new ResponseEntity<>(penaContractualService.obtenerDocumentosContratoFuncional(idContrato, tipo), HttpStatus.OK);
	}

	@DeleteMapping("/eliminar-pena-contractual")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<List<Long>> eliminarRegistroPenasContractuales(@RequestBody Map<String, List<Long>> request) {
		return new ResponseEntity<>(penaContractualService.eliminarRegistro(request), HttpStatus.OK);
	}

}
