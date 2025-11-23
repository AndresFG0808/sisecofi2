package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaTipoDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteTipo;

public interface ReporteTipoService {
    PageReporteTipo obtenerReporte(ConsultaTipoDTO dto);

    byte[] obtenerReporteExport(ConsultaTipoDTO dto);
}
