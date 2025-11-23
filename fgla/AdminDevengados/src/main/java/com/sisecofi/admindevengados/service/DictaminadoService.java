package com.sisecofi.admindevengados.service;

import java.util.List;
import com.sisecofi.admindevengados.dto.DictaminadoDto;
import com.sisecofi.admindevengados.dto.EstimacionDto;

public interface DictaminadoService {

	List<DictaminadoDto> guardarServicioDictaminado(List<DictaminadoDto> serviciosSeleccionados);

	List<EstimacionDto> obtenerEstimaciones(Long idContrato, Long idProveedor);

	List<DictaminadoDto> obtenerDictaminados(Long idContrato, Long dictamenId);

	List<DictaminadoDto> actualizarDictaminados(List<DictaminadoDto> listaDictaminados, String idEstimacion);

}
