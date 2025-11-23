package com.sisecofi.admindevengados.controller;

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

import com.sisecofi.admindevengados.dto.DictaminadoDto;
import com.sisecofi.admindevengados.dto.EstimacionDto;
import com.sisecofi.admindevengados.dto.GenerarExcelDto;
import com.sisecofi.admindevengados.dto.PosicionCantidadDto;
import com.sisecofi.admindevengados.service.DictaminadoService;
import com.sisecofi.admindevengados.service.ServicioReporteDictaminado;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DictaminadoController {

	private final DictaminadoService dictaminadoService;
	private final ServicioReporteDictaminado servicioDictaminadoService;

	@PostMapping("/obtener-servicios/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<DictaminadoDto>>> obtenerDictaminados(@PathVariable Long idContrato,
			@RequestBody DictamenId dictamenId) {
		List<DictaminadoDto> contrato = dictaminadoService.obtenerDictaminados(idContrato, dictamenId.getIdDictamen());
		ResponseGeneric<List<DictaminadoDto>> data = ResponseGeneric.<List<DictaminadoDto>>builder().build();
		data.setData(contrato);
		data.setMsj(ErroresEnum.CONTRATO_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/actualizar-servicio-contrato")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")	@Transactional
	public ResponseEntity<ResponseGeneric<List<DictaminadoDto>>> actualizarDictaminados(
			@Valid @RequestBody PosicionCantidadDto pisiciones) {
		List<DictaminadoDto> lista = pisiciones.getListaDictaminados();
		List<DictaminadoDto> contrato = dictaminadoService.actualizarDictaminados(lista, pisiciones.getIdEstimacion());
		ResponseGeneric<List<DictaminadoDto>> data = ResponseGeneric.<List<DictaminadoDto>>builder().build();
		data.setData(contrato);
		data.setMsj(ErroresEnum.CONTRATO_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/generar-excel-dictaminado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen()")
	@Transactional
	public ResponseEntity<ResponseGeneric<String>> generarExcel(@RequestBody GenerarExcelDto exceldto) {
		String base64 = servicioDictaminadoService.obtenerInformacion(exceldto.getLista());
		ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
		data.setData(base64);
		data.setMsj(ErroresEnum.DICTAMEN_EXPORTAR_EXCEL);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/guardar-servicio-dictaminado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<DictaminadoDto>>> guardarServicioDictaminado(
			@RequestBody @Valid List<DictaminadoDto> serviciosSeleccionados) {
		List<DictaminadoDto> contrato = dictaminadoService.guardarServicioDictaminado(serviciosSeleccionados);
		ResponseGeneric<List<DictaminadoDto>> data = ResponseGeneric.<List<DictaminadoDto>>builder().build();
		data.setData(contrato);
		data.setMsj(ErroresEnum.DICTAMINADO_REGISTRO);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}
	@GetMapping("/obtener-estimaciones/{idContrato}/{idProveedor}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<EstimacionDto>>> obtenerEstimaciones(
	        @PathVariable Long idContrato, 
	        @PathVariable Long idProveedor) {
	    List<EstimacionDto> estimaciones = dictaminadoService.obtenerEstimaciones(idContrato, idProveedor);
	    ResponseGeneric<List<EstimacionDto>> data = ResponseGeneric.<List<EstimacionDto>>builder().build();
	    data.setData(estimaciones);
	    data.setMsj(ErroresEnum.ESRIMACION_OBTENIDA);
	    return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
