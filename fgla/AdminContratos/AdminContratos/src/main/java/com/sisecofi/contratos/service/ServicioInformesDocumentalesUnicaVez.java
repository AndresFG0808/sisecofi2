package com.sisecofi.contratos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;

public interface ServicioInformesDocumentalesUnicaVez {
	String guardarInformeDocumentalUnicaVez(List<InformesDocumentalesUnicaVezModel> informe);
	List<InformesDocumentalesUnicaVezModel> obtenerInformesDocumentalesUnicaVez(Long idContrato);
	String eliminarInformeDocumentalUnicaVez(List<Long> id);
	String actualizarInformeDocumentalUnicaVez(List<InformesDocumentalesUnicaVezModel> ids);
	String exportarExcel(Long idContrato);
	InformesDocumentalesUnicaVezModel obtenerInformeDocumentalUv(Long idInforme);
}
