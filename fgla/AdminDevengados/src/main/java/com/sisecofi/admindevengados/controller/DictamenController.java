package com.sisecofi.admindevengados.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.dictamen.DictamenRequest;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.admindevengados.dto.DuplicarDictamenDto;
import com.sisecofi.admindevengados.dto.ResumenConsolidadoDto;
import com.sisecofi.admindevengados.model.ResumenConsolidadoModel;
import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.ServiceReporteDictamenes;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import com.sisecofi.admindevengados.dto.CancelarDictamenDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DictamenController {

	private final DictamenService dictamenService;
	private final ServiceReporteDictamenes serviceReporteDictamenes;
	private final CatalogoService catalogoService;

	@PostMapping("/dictamen-guardar/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ResponseGeneric<Dictamen>> guardarDictamen(@PathVariable Long idContrato,
			@RequestBody @Valid dictamenDto dictamendto) {

		Dictamen dictamen = dictamenService.guardarDictamen(idContrato, dictamendto);

		ResponseGeneric<Dictamen> data = ResponseGeneric.<Dictamen>builder().build();
		data.setData(dictamen);
		data.setMsj(ErroresEnum.DICTAMEN_CREADO);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PutMapping("/ultima-modificacion-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
	public ResponseEntity<ResponseGeneric<String>> ultimaModificacionGeneral() {

		String dictamen = dictamenService.ultimaModificacionGeneral();

		ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
		data.setData(dictamen);
		data.setMsj(ErroresEnum.DICTAMEN_CREADO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/validar-tipo-cambio/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<ResponseGeneric<Integer>> validarTipoCambio(@PathVariable Long idContrato) {

		Integer estatusTipoCambio = dictamenService.validarTipoCambio(idContrato);
		ResponseGeneric<Integer> data = ResponseGeneric.<Integer>builder().build();
		data.setData(estatusTipoCambio);
		data.setMsj(ErroresEnum.TIPO_CAMBIO_VALIDADO);
		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@GetMapping("/AplicaCC/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<Boolean>> aplicaCC(@PathVariable Long idContrato) {

		Boolean cc = catalogoService.aplicaCC(idContrato);
		ResponseGeneric<Boolean> data = ResponseGeneric.<Boolean>builder().build();
		data.setData(cc);
		data.setMsj(ErroresEnum.TIPO_CAMBIO_VALIDADO);
		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@GetMapping("/obtener-dictamenes")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
	public List<Dictamen> obtenerDictamenes() {
		return dictamenService.obtenerDictamenes();
	}

	@PostMapping("/obtener-dictamenes-id")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<dictamenDto>> obtenerDictamenesId(@RequestBody dictamenDto idDictamen) {
		dictamenDto dictamen = dictamenService.obtenerDictamenesId(idDictamen.getIdDictamen());
		ResponseGeneric<dictamenDto> data = ResponseGeneric.<dictamenDto>builder().build();
		data.setData(dictamen);
		data.setMsj(ErroresEnum.DICTAMEN_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@GetMapping("/obtener-contratoDto/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<ResponseGeneric<ContratoDto>> obtenerContratoDto(@PathVariable Long idContrato) {
		ContratoDto contrato = dictamenService.obtenerContratoDto(idContrato);
		ResponseGeneric<ContratoDto> data = ResponseGeneric.<ContratoDto>builder().build();
		data.setData(contrato);
		data.setMsj(ErroresEnum.CONTRATO_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/obtener-contrato-mod/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()")
	public ResponseEntity<ResponseGeneric<ContratoDtoLigeroComun>> obtenerContratoMod(@PathVariable Long idContrato) {
		ContratoDtoLigeroComun contrato = dictamenService.obtenerContratoLigero(idContrato);
		ResponseGeneric<ContratoDtoLigeroComun> data = ResponseGeneric.<ContratoDtoLigeroComun>builder().build();
		data.setData(contrato);
		data.setMsj(ErroresEnum.CONTRATO_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping("/cancelar-estatus")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
	public boolean cancelarDictamen(@RequestBody CancelarDictamenDto cancelarDictamenDto) {
		return dictamenService.cancelarDictamen(cancelarDictamenDto.getDictamenId().getIdDictamen(),
				cancelarDictamenDto.getDescripcion());
	}

	@PostMapping("/anterior")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<List<Dictamen>>> anterior(@RequestBody DictamenId dictamenId) {
		List<Dictamen> dictamenes = dictamenService.anterior(dictamenId.getIdDictamen());
		ResponseGeneric<List<Dictamen>> data = ResponseGeneric.<List<Dictamen>>builder().build();
		data.setData(dictamenes);
		data.setMsj(ErroresEnum.DICTAMEN_ANTERIOR_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/siguiente")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<List<Dictamen>>> siguiente(@RequestBody DictamenId dictamenId) {
		List<Dictamen> dictamenes = dictamenService.siguiente(dictamenId.getIdDictamen());
		ResponseGeneric<List<Dictamen>> data = ResponseGeneric.<List<Dictamen>>builder().build();
		data.setData(dictamenes);
		data.setMsj(ErroresEnum.DICTAMEN_SIGUIENTE_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/validarExisteAnteriorSiguiente")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "||  @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolLiderProyecto() " + "|| @seguridad.validarRolUsuarioConsulta() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<Integer>> validarDictamenAnteriorYSiguiente(
			@RequestBody DictamenId dictamenId) {
		Integer dictamenes = dictamenService.validarDictamenAnteriorYSiguiente(dictamenId.getIdDictamen());
		ResponseGeneric<Integer> data = ResponseGeneric.<Integer>builder().build();
		data.setData(dictamenes);
		data.setMsj(ErroresEnum.DICTAMEN_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/obtener-dictamenes-contrato/{idContrato}")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<ResponseGeneric<List<Dictamen>>> obtenerContratoDictamene(@PathVariable Long idContrato) {
		List<Dictamen> dictamenContrato = dictamenService.obtenerContratosDictamen(idContrato);
		ResponseGeneric<List<Dictamen>> data = ResponseGeneric.<List<Dictamen>>builder().build();
		data.setData(dictamenContrato);
		data.setMsj(ErroresEnum.DICTAMEN_CONTRATO_OBTENIDO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping("/editar-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ResponseGeneric<Dictamen>> actualizarDictamen(
			@Valid @RequestBody DictamenRequest dictamenRequest) {
		Dictamen dictamen = dictamenService.editarDictamen(dictamenRequest.getDictamenId().getIdDictamen(),
				dictamenRequest.getDictamendto());
		ResponseGeneric<Dictamen> data = ResponseGeneric.<Dictamen>builder().build();
		data.setData(dictamen);
		data.setMsj(ErroresEnum.DICTAMEN_ACTUALIZADO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/generar-excel")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ResponseGeneric<String>> generarExcel(@RequestBody DictamenId dictamenId) {
		String base64 = serviceReporteDictamenes.obtenerReporteResumenConsolidado(dictamenId.getIdDictamen());
		ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
		data.setData(base64);
		data.setMsj(ErroresEnum.DICTAMEN_EXPORTAR_EXCEL);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/resumen-consolidado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<List<ResumenConsolidadoDto>>> resumenConsolidado(
			@RequestBody DictamenId idDictamen) {

		List<ResumenConsolidadoDto> resumen = dictamenService.obtenerResumenConsolidado(idDictamen.getIdDictamen());
		ResponseGeneric<List<ResumenConsolidadoDto>> data = ResponseGeneric.<List<ResumenConsolidadoDto>>builder()
				.build();
		data.setData(resumen);
		data.setMsj(ErroresEnum.DICTAMEN_RESUMEN_CONSOLIDADO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping("/actualizar-estatus-inicial")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<ResponseGeneric<Dictamen>> actualizarEstatusInicial(@RequestBody DictamenId idDictamen) {

		Dictamen resumen = dictamenService.actualizarEstatusInicial(idDictamen.getIdDictamen());
		ResponseGeneric<Dictamen> data = ResponseGeneric.<Dictamen>builder().build();
		data.setData(resumen);
		data.setMsj(ErroresEnum.DICTAMEN_RESUMEN_CONSOLIDADO);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/duplicar-dictamen")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<Dictamen> duplicarDictamen(@RequestBody DuplicarDictamenDto duplicarDictamenDto) {
		return new ResponseEntity<>(
				dictamenService.duplicarDictamen(duplicarDictamenDto.getDictamenId().getIdDictamen(),
						duplicarDictamenDto.getRegistrosDictaminados(), duplicarDictamenDto.getPenasContractuales(),
						duplicarDictamenDto.getPenasConvencionales(), duplicarDictamenDto.getDeducciones(),
						duplicarDictamenDto.getDictamenDto()),
				HttpStatus.OK);
	}

	@GetMapping("/validar-iva/{idProyecto}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolApoyoAcppi() " + "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolUsuarioConsulta() " + "|| @seguridad.validarRolLiderProyecto() "
			+ "|| @seguridad.validarRolAdministradorContrato() "
			+ "|| @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> validarIva(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(dictamenService.validarIva(idProyecto), HttpStatus.OK);
	}

	@PutMapping("/actualizar-resumen-facturado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ResumenConsolidadoModel>> actualizarResumeFacturado(@RequestBody DictamenId idDictamen) {
		return new ResponseEntity<>(dictamenService.actualizarResumeFacturado(idDictamen.getIdDictamen()),
				HttpStatus.OK);
	}

	@PutMapping("/actualizar-resumen-consolidado")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<ResumenConsolidadoModel>> actualizarResumenConsolidado(
			@RequestBody DictamenId idDictamen) {
		return new ResponseEntity<>(dictamenService.actualizarResumenConsolidado(idDictamen.getIdDictamen()),
				HttpStatus.OK);
	}

	@PutMapping("/actualizar-estatus-penas-deducciones")
	@PreAuthorize("@seguridad.validarRolAdminSistema() ||  @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral()")
	public ResponseEntity<String> actualizarCheckPenasDeducciones(@RequestParam("idDictamen") DictamenId idDictamen,
			@RequestParam(name = "checkContractual", required = false) Boolean checkContractual,
			@RequestParam(name = "checkConvencional", required = false) Boolean checkConvencional,
			@RequestParam(name = "checkDeducciones", required = false) Boolean checkDeducciones) {

		return new ResponseEntity<>(
				dictamenService.actualizarCheckPenasDeducciones(idDictamen.getIdDictamen(), checkContractual,
						checkConvencional, checkDeducciones),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/" +Constantes.PATH_BASE_DICTAMEN+"/obtener-archivos-cloud/{idDictamen}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " + "|| @seguridad.validarRolAdminSistemaSecundario() "
			+ "|| @seguridad.validarRolAdminMatrizDocumental() " + "|| @seguridad.validarRolApoyoAcppi() "
			+ "|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() "
			+ "|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<Boolean> obtenerArchivosCloud(@PathVariable Long idDictamen) throws NexusException{
        return new ResponseEntity<>(dictamenService.sincronizarArchivos(idDictamen), HttpStatus.OK);
    }

}
