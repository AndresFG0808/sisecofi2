package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoDictamenDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteSeguimientoDictamen;

public interface ReporteSeguimientoDictamenService {
    PageReporteSeguimientoDictamen obtenerReporte(ConsultaSeguimientoDictamenDTO dto);

    byte[] obtenerReporteExport(ConsultaSeguimientoDictamenDTO dto);
}
