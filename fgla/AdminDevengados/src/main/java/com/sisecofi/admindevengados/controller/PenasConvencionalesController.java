package com.sisecofi.admindevengados.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;
import com.sisecofi.admindevengados.service.PenasConvencionalesService;
import com.sisecofi.admindevengados.service.ServicioReportePenasConvencionales;
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
public class PenasConvencionalesController {

	private final PenasConvencionalesService penasConvencionalesService;
	private final ServicioReportePenasConvencionales servicioReportePenasConvencionales;

	@PostMapping("/penas-convencionales-guardar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<PenasConvencionalesModel>>> guardarPenasConvencionales(
			@RequestBody @Valid List<ObtenerPenaContractualDto> penas) {

		List<PenasConvencionalesModel> penass = penasConvencionalesService.guardarPenaConvencional(penas);

		ResponseGeneric<List<PenasConvencionalesModel>> data = ResponseGeneric.<List<PenasConvencionalesModel>>builder()
				.build();
		data.setData(penass);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PutMapping("/penas-convencionales-modificar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<PenasConvencionalesModel>>> modificarPenasConvencionales(
			@RequestBody @Valid List<ObtenerPenaContractualDto> penas) {

		List<PenasConvencionalesModel> penass = penasConvencionalesService.modificarPenaConvencional(penas);

		ResponseGeneric<List<PenasConvencionalesModel>> data = ResponseGeneric.<List<PenasConvencionalesModel>>builder()
				.build();
		data.setData(penass);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/penas-convencionales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<ObtenerPenaContractualDto>>> obtenerPenasConvencionales(
			@RequestBody DictamenId dictamenId) {

		List<ObtenerPenaContractualDto> penas = penasConvencionalesService
				.obtenerPenasConvencional(dictamenId.getIdDictamen());

		ResponseGeneric<List<ObtenerPenaContractualDto>> data = ResponseGeneric
				.<List<ObtenerPenaContractualDto>>builder().build();
		data.setData(penas);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/generar-excel-penas-convencionales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<String>> generarExcelPenasConvencionales(
			@RequestBody List<PenaContractualExcelDto> lista) {
		String base64 = servicioReportePenasConvencionales.obtenerInformacion(lista);
		ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
		data.setData(base64);
		data.setMsj(ErroresEnum.DICTAMEN_EXPORTAR_EXCEL);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar-pena-convencional")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<List<Long>> eliminarRegistroPenasConvencionales(
			@RequestBody Map<String, List<Long>> request) {
		return new ResponseEntity<>(penasConvencionalesService.eliminarRegistro(request), HttpStatus.OK);
	}

}
