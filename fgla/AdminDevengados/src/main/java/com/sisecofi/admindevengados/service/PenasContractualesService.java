package com.sisecofi.admindevengados.service;

import java.util.List;
import java.util.Map;

import com.sisecofi.admindevengados.dto.ObtenerPenaContractualDto;
import com.sisecofi.libreria.comunes.dto.dictamen.PenasContractualesByIdDto;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;

public interface PenasContractualesService {

	List<PenasContractualesModel> guardarPenaContractual(List<ObtenerPenaContractualDto> penas);

	List<PenasContractualesModel> modificarPenaContractual(List<ObtenerPenaContractualDto> penasDto);

	List<ObtenerPenaContractualDto> obtenerPenasContractuales(Long dictamenId);

	List<Long> eliminarRegistro(Map<String, List<Long>> request);

	<T> List<T> obtenerDocumentosContratoFuncional(Long idContrato, String tipo);

	List<PenasContractualesModel> obtenerPenasContractualesPorIds(PenasContractualesByIdDto penasContractualesByIdDto);

	<T> List<ObtenerPenaContractualDto> construirListaPenas(List<T> penas, Long dictamenId);

}
