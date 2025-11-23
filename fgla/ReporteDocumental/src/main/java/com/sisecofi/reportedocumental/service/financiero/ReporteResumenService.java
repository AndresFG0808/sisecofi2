package com.sisecofi.reportedocumental.service.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConsultaDetalleCMDTO;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaResumenDTO;
import com.sisecofi.reportedocumental.dto.financiero.PageReporteDetalleCM;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteFinanciero;

public interface ReporteResumenService {
    PageReporteFinanciero obtenerReporte(ConsultaResumenDTO dto);

    PageReporteDetalleCM obtenerReporteDetalleCM(ConsultaDetalleCMDTO dto);

    byte[] obtenerReporteExport(ConsultaResumenDTO dto);

    byte[]  obtenerReporteDetalleCMExport(ConsultaDetalleCMDTO dto);
}
