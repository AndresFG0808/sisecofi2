package com.sisecofi.contratos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;

public interface ServicioInformesDocumentalesServicios {
	List<InformesDocumentalesServiciosModel> obtenerInformesDocumentalesServicios(Long idContrato);

	String guardarInformeDocumentalServicios(List<InformesDocumentalesServiciosModel> informe);

	String eliminarInformeDocumentalServicios(List<Long> id);

	String actualizarInformeDocumentalServicios(List<InformesDocumentalesServiciosModel> ids);

	String exportarExcel(Long idContrato);

	InformesDocumentalesServiciosModel obtenerInformeDocumental(Long idInforme);
}
