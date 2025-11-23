package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstimadoPagadoDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteEstimadoPagado;

public interface ReporteEstimadoPagadoService {
    PageReporteEstimadoPagado obtenerReporte(ConsultaEstimadoPagadoDTO dto);

    byte[] obtenerReporteExport(ConsultaEstimadoPagadoDTO dto);
}
