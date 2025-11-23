package com.sisecofi.admindevengados.controller.facturas;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.EstatusPagadoDto;
import com.sisecofi.admindevengados.dto.FacturaDto;
import com.sisecofi.admindevengados.dto.FacturaGuardarDto;
import com.sisecofi.admindevengados.service.facturas.FacturaService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.dto.factura.WebServiceDto;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.service.PathService;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;
import com.webservice.cfdi.util.exception.CfdiException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class FacturasController {

	private final FacturaService facturaService;
	private final PathService pathService;
	private static final String ERROR_RESPONSE = "ERROR";
    private static final String SUCCESS_RESPONSE = "PAGADO";

	@PostMapping("/obtener-datosXML")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<FacturaDto>> obtenerDatosXML(@ModelAttribute Dictamen dictamen, String seccion,
			@Valid @RequestPart(value = "archivoXml", required = false) MultipartFile archivoXml) throws CfdiException {

		if (!pathService.comprobarArchivo(archivoXml)) {
            return ResponseEntity.badRequest().body(null);
        }
		
		FacturaDto factura = facturaService.obtenerDatosXML(archivoXml, dictamen.getIdContrato(),
				dictamen.getIdProveedor(), dictamen.getIdDictamen(), seccion);

		ResponseGeneric<FacturaDto> data = ResponseGeneric.<FacturaDto>builder().build();
		data.setData(factura);
		data.setMsj(ErroresEnum.FACTURA_GUARDADA);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/guardar-factura")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<FacturaModel>> guardarDictamen(
			@ModelAttribute @Valid FacturaGuardarDto facturaDto,
			@RequestPart(value = "archivoXml", required = false) MultipartFile archivoXml,
			@RequestPart(value = "pdf", required = false) MultipartFile pdf) throws CfdiException {

		if (!pathService.comprobarArchivo(archivoXml)) {
            return ResponseEntity.badRequest().body(null);
        }
		if (!pathService.comprobarArchivo(pdf)) {
            return ResponseEntity.badRequest().body(null);
        }
		FacturaModel factura = facturaService.guardarFactura(facturaDto, archivoXml, pdf);

		ResponseGeneric<FacturaModel> data = ResponseGeneric.<FacturaModel>builder().build();
		data.setData(factura);
		data.setMsj(ErroresEnum.FACTURA_GUARDADA);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PostMapping("/editar-factura")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<ResponseGeneric<Object>> editarFactura(@ModelAttribute @Valid FacturaGuardarDto facturaDto,
			@RequestPart(value = "archivoXml", required = false) MultipartFile archivoXml,
			@RequestPart(value = "pdf", required = false) MultipartFile pdf) throws CfdiException {

		if (!pathService.comprobarArchivo(archivoXml)) {
            return ResponseEntity.badRequest().body(null);
        }
		if (!pathService.comprobarArchivo(pdf)) {
            return ResponseEntity.badRequest().body(null);
        }
		Object factura = facturaService.editarFactura(facturaDto, archivoXml, pdf);

		ResponseGeneric<Object> data = ResponseGeneric.<Object>builder().build();
		data.setData(factura);
		data.setMsj(ErroresEnum.FACTURA_GUARDADA);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/guardar-nota")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<NotaCreditoModel>> guardarNotaCredito(
			@ModelAttribute @Valid FacturaGuardarDto facturaDto,
			@RequestPart(value = "archivoXml", required = false) MultipartFile archivoXml,
			@RequestPart(value = "pdf", required = false) MultipartFile pdf) throws CfdiException {

		if (!pathService.comprobarArchivo(archivoXml)) {
            return ResponseEntity.badRequest().body(null);
        }
		if (!pathService.comprobarArchivo(pdf)) {
            return ResponseEntity.badRequest().body(null);
        }
		NotaCreditoModel factura = facturaService.guardarNotaCredito(facturaDto, archivoXml, pdf);

		ResponseGeneric<NotaCreditoModel> data = ResponseGeneric.<NotaCreditoModel>builder().build();
		data.setData(factura);
		data.setMsj(ErroresEnum.FACTURA_GUARDADA);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PostMapping("/obtener-facturas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<FacturaModel>>> obtenerFacturas(@RequestBody DictamenId dictamenId) {

		List<FacturaModel> facturas = facturaService.obtenerFacturas(dictamenId.getIdDictamen());

		ResponseGeneric<List<FacturaModel>> data = ResponseGeneric.<List<FacturaModel>>builder().build();
		data.setData(facturas);
		data.setMsj(ErroresEnum.VALIDAR_FACTURAS_RECIBIDAS);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PostMapping("/obtener-notas-credito")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<List<NotaCreditoModel>>> obtenerNotasCredito(
			@RequestBody DictamenId dictamenId) {

		List<NotaCreditoModel> facturas = facturaService.obtenerNotasCredito(dictamenId.getIdDictamen());

		ResponseGeneric<List<NotaCreditoModel>> data = ResponseGeneric.<List<NotaCreditoModel>>builder().build();
		data.setData(facturas);
		data.setMsj(ErroresEnum.VALIDAR_FACTURAS_RECIBIDAS);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@PostMapping("/cancelar-factura/{idFactura}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<FacturaModel>> cancelarFactura(@PathVariable Long idFactura,
			@RequestBody String justificacion) {

		FacturaModel factura = facturaService.cancelarFactura(idFactura, justificacion);

		ResponseGeneric<FacturaModel> data = ResponseGeneric.<FacturaModel>builder().build();
		data.setData(factura);
		data.setMsj(ErroresEnum.FACTURA_GUARDADA);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/cancelar-nota-credito/{idNota}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<ResponseGeneric<NotaCreditoModel>> cancelarNota(@PathVariable Long idNota,
			@RequestBody String justificacion) {

		NotaCreditoModel notaCredito = facturaService.cancelarNota(idNota, justificacion);

		ResponseGeneric<NotaCreditoModel> data = ResponseGeneric.<NotaCreditoModel>builder().build();
		data.setData(notaCredito);
		data.setMsj(ErroresEnum.NOTA_CANCELADA);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/validar-facturas-notas-asociadas")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	@Transactional
	public ResponseEntity<Boolean> validarRegresarProforma(@RequestBody DictamenId dictamenId) {
		return new ResponseEntity<>(facturaService.validarFacturasNotasAsociadas(dictamenId.getIdDictamen()), HttpStatus.OK);
	}

	@PostMapping("/regresar-proforma")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() ||  @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
	@Transactional
	public ResponseEntity<Boolean> regresarProforma(@RequestBody DictamenId dictamenId) {
		return new ResponseEntity<>(facturaService.regresarProforma(dictamenId.getIdDictamen() ), HttpStatus.OK);
	}

	@PostMapping("/validar-web-service")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() ||  @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen()")
	@Transactional
	public ResponseEntity<ConsultaCfdiResponse> validarWebService(@RequestBody WebServiceDto webServiceDto)
			throws CfdiException {
		return new ResponseEntity<>(facturaService.validarWebService(webServiceDto), HttpStatus.OK);
	}


	@PutMapping("/solicitud-factura-pagado/{idDictamen}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<EstatusPagadoDto> pagarDictamen(@PathVariable @NotNull Long idDictamen) {
        
        try {
            boolean resultado =facturaService.pagarDictamen(idDictamen);
            
            if (resultado) {
                EstatusPagadoDto response = new EstatusPagadoDto(idDictamen, SUCCESS_RESPONSE);
                return ResponseEntity.ok(response);
            } else {
                EstatusPagadoDto response = new EstatusPagadoDto(idDictamen, ERROR_RESPONSE);
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (CatalogoException e) {
            EstatusPagadoDto response = new EstatusPagadoDto(idDictamen, ERROR_RESPONSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    
        } catch (IllegalStateException e) {
            EstatusPagadoDto response = new EstatusPagadoDto(idDictamen, ERROR_RESPONSE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    

        } catch (Exception e) {
            EstatusPagadoDto response = new EstatusPagadoDto(idDictamen, ERROR_RESPONSE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



}
