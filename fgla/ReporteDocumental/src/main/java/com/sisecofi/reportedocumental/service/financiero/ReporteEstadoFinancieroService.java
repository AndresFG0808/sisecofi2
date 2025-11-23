package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstadoFinancieroDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageEstadoFinanciero;

public interface ReporteEstadoFinancieroService {
    PageEstadoFinanciero obtenerReporte(ConsultaEstadoFinancieroDTO dto);

    byte[] obtenerReporteExport(ConsultaEstadoFinancieroDTO dto);
}
