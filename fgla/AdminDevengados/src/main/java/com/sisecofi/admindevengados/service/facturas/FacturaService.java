package com.sisecofi.admindevengados.service.facturas;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.BanderaReponseDto;
import com.sisecofi.admindevengados.dto.FacturaDto;
import com.sisecofi.admindevengados.dto.FacturaGuardarDto;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import com.sisecofi.libreria.comunes.dto.factura.WebServiceDto;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;
import com.webservice.cfdi.util.exception.CfdiException;

import jakarta.validation.Valid;

public interface FacturaService {
	
	FacturaModel guardarFactura(FacturaGuardarDto facturaDto, MultipartFile archivoXml, MultipartFile pdf) throws CfdiException;
	
	List<FacturaModel> obtenerFacturas(Long dictamenId);

	FacturaDto obtenerDatosXML(MultipartFile archivoXml, Long idContrato, Long idProveedor, Long dictamenId, String seccion) throws CfdiException;
	
	List<FacturaContratoDto> obtenerFacturasContrato(Long idContrato);

	String cargarArchivo(String dictamenId, MultipartFile files);

	@Valid
	NotaCreditoModel guardarNotaCredito(@Valid FacturaGuardarDto facturaDto, MultipartFile archivoXml, MultipartFile pdf) throws CfdiException;

	List<NotaCreditoModel> obtenerNotasCredito(Long dictamenId);

	FacturaModel cancelarFactura(Long idFactura, String justificacion);
	List<FacturaContratoDto> obtenerFacturasEstimacion(List<Dictamen> lista);

	@Valid
	<T> T editarFactura(@Valid FacturaGuardarDto facturaDto, MultipartFile archivoXml, MultipartFile pdf) throws CfdiException;

	NotaCreditoModel cancelarNota(Long idNota, String justificacion);

	boolean validarFacturasNotasAsociadas(Long dictamenId);

	boolean regresarProforma(Long dictamenId);

	ConsultaCfdiResponse validarWebService(WebServiceDto webServiceDto) throws CfdiException;

	BanderaReponseDto banderaPagadofactura(Long idDictamen);

	boolean pagarDictamen(Long idDictamen);

	
}
