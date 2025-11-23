package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.reportedocumental.dto.financiero.ConceptoServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoBaseDTO;
import com.sisecofi.reportedocumental.dto.financiero.NombreContratoDTO;
import com.sisecofi.reportedocumental.dto.financiero.VerificadorContratoDto;
import com.sisecofi.reportedocumental.model.CatEstatusVolumetria;

import java.util.List;

public interface CatalogoReporteFinancieroService {
    List<BaseCatalogoModel> estatusProyecto();

    List<BaseCatalogoModel> contratoVigente();

    List<BaseCatalogoModel> dominiosTecnologicos();

    List<BaseCatalogoModel> convenioColaboracion();

    List<ProyectoNombreDto> buscarTodosProyectos();

    List<ProyectoNombreDto> buscarProyectosPorEstatus(Integer idEstatusProyecto);

    List<NombreContratoDTO> filtrarContratos(FiltroNombreContratoBaseDTO dto);

    List<BaseCatalogoModel> estatusDictamen();

    List<VerificadorContratoDto> listarVerificadoresPorContrato(String nombreCortoContrato);

    List<CatEstatusVolumetria> estatusVolumetria();

    List<ConceptoServicioDTO> conceptoServicio(Long idContrato);
}
