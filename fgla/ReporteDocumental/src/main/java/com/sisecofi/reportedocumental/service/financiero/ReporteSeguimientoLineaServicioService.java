package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoLineaServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageSeguimientoLineaServicio;

public interface ReporteSeguimientoLineaServicioService {
    PageSeguimientoLineaServicio obtenerReporte(ConsultaSeguimientoLineaServicioDTO dto);

    byte[] obtenerReporteExport(ConsultaSeguimientoLineaServicioDTO dto);
}
