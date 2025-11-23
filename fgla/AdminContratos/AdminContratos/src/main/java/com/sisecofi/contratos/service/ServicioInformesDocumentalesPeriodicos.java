package com.sisecofi.contratos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;


public interface ServicioInformesDocumentalesPeriodicos {
	List<InformesDocumentalesPeriodicosModel> obtenerInformesDocumentalesPeriodicos(Long idContrato);
	String guardarInformeDocumentalPeridicos(List<InformesDocumentalesPeriodicosModel> informe);
	String eliminarInformeDocumentalPeriodicos(List<Long> id);
	String actualizarInformeDocumentalPeriodicos(List<InformesDocumentalesPeriodicosModel> ids);
	String exportarExcel(Long idContrato);
	InformesDocumentalesPeriodicosModel obtenerInformeDocumentalPeriodico(Long idInforme);
}
