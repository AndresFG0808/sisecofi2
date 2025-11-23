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

import com.sisecofi.admindevengados.dto.CatalogoConceptosDto;
import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.admindevengados.dto.ObtenerPenaContractualRequestDto;
import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;
import com.sisecofi.admindevengados.service.DeduccionesService;
import com.sisecofi.admindevengados.service.ServicioReporteDeducciones;
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
public class DeduccionesController {

	private final DeduccionesService deduccionesService;
	private final ServicioReporteDeducciones servicioReporteDeducciones;

	@PostMapping("/deducciones-guardar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ResponseGeneric<List<ObtenerPenaContractualDto>>> guardarDeduccion(
        @RequestBody @Valid List<ObtenerPenaContractualRequestDto> penas) {

    List<ObtenerPenaContractualDto> respuesta = deduccionesService.guardarDeduccion(penas);

    ResponseGeneric<List<ObtenerPenaContractualDto>> data = ResponseGeneric
            .<List<ObtenerPenaContractualDto>>builder()
            .data(respuesta)
            .msj(ErroresEnum.PENA_CONTRACTUAL_REGISTRO)
            .build();

    return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PutMapping("/deducciones-modificar")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<ObtenerPenaContractualDto>>> modificarDeduccion(
			@RequestBody @Valid List<ObtenerPenaContractualDto> penas) {

		List<ObtenerPenaContractualDto> penass = deduccionesService.modificarDeduccion(penas);

		ResponseGeneric<List<ObtenerPenaContractualDto>> data = ResponseGeneric.<List<ObtenerPenaContractualDto>>builder().build();
		data.setData(penass);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/deducciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<ObtenerPenaContractualDto>>> obtenerDeducciones(
			@RequestBody DictamenId dictamenId) {

		List<ObtenerPenaContractualDto> penas = deduccionesService.obtenerDeducciones(dictamenId.getIdDictamen());

		ResponseGeneric<List<ObtenerPenaContractualDto>> data = ResponseGeneric
				.<List<ObtenerPenaContractualDto>>builder().build();
		data.setData(penas);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/generar-excel-deducciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<String>> generarExcelDeducciones(
			@RequestBody List<PenaContractualExcelDto> lista) {
		String base64 = servicioReporteDeducciones.obtenerInformacion(lista);
		ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
		data.setData(base64);
		data.setMsj(ErroresEnum.DICTAMEN_EXPORTAR_EXCEL);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/obtener-conceptos-servicio")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<String>>> obtenerConceptosServicio(
			@RequestBody CatalogoConceptosDto catalogoConceptosDto) {
		List<String> conceptos = deduccionesService.obtenerConceptosServicio(catalogoConceptosDto.getTipo(),
				catalogoConceptosDto.getDictamenId().getIdDictamen()   );

		ResponseGeneric<List<String>> data = ResponseGeneric.<List<String>>builder().build();
		data.setData(conceptos);
		data.setMsj(ErroresEnum.PENA_CONTRACTUAL_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar-deducciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<List<Long>> eliminarRegistroDeducciones(@RequestBody Map<String, List<Long>> request) {
		return new ResponseEntity<>(deduccionesService.eliminarRegistro(request), HttpStatus.OK);
	}

}
