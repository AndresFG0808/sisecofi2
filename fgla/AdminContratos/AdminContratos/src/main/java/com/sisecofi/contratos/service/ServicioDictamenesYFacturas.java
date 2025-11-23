package com.sisecofi.contratos.service;

import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;

import java.util.List;

public interface ServicioDictamenesYFacturas {

	List<FacturaContratoDto> obtenerFacturasContrato(Long idContrato);
	
	   String exportarFacturasAsociadas(Long idContrato);
	   
	   String exportarDictamenesAsociados(Long idContrato);

}
