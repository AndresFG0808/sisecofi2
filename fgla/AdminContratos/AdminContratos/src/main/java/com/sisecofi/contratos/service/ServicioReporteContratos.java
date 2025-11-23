package com.sisecofi.contratos.service;

import com.sisecofi.contratos.dto.CriteriosDeBusquedaContratoDto;

public interface ServicioReporteContratos {
    String obtenerReporteContratosRegistrados(CriteriosDeBusquedaContratoDto contrato);

    String obtenerReporteServicioConttrato(Long idContrato);

    String obtenerReporteAtrasoPrestacion(Long idContrato);

    String obtenerReporteParticipantesContrato(Long idContrato);

    String obtenerReporteReintegroAsociado(Long idContrato);

    String obtenerReporteConvenioModificatorio(Long idContrato);
}
