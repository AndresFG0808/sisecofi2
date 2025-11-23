package com.sisecofi.contratos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;

public interface ServicioPenasContractuales {

	Boolean crearPena (List<PenaContractualContratoModel> request, Long idContrato);

	Boolean editarPena (List<PenaContractualContratoModel> pena);
	
	Boolean eliminarPena (List<Long> id);
	
	List<PenaContractualContratoModel> obtenerPenas(Long idContrato);
	
	String generarReporte(Long idContrato);

}
