package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaDetalleCMDTO;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaResumenDTO;
import com.sisecofi.reportedocumental.dto.financiero.PageReporteDetalleCM;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteFinanciero;
import com.sisecofi.reportedocumental.repository.financiero.ReporteResumenDetalleCMRepository;
import com.sisecofi.reportedocumental.repository.financiero.ReporteResumenRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.financiero.ReporteResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteResumenServiceImpl extends ReporteFinancieroServiceBase implements ReporteResumenService {

    private final ReporteResumenRepository reporteResumenRepository;
    private final ReporteResumenDetalleCMRepository reporteResumenDetalleCMRepository;
    private final ReportExportGenericService exportGenericService;
    private final PistaService microservicio;

    @Qualifier("mapperResumen")
    private final MapperResumen mapperResumen;

    @Override
    public PageReporteFinanciero obtenerReporte(ConsultaResumenDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return new PageReporteFinanciero(dto, reporteResumenRepository.obtenerReporte(dto, true));
    }

    @Override
    public PageReporteDetalleCM obtenerReporteDetalleCM(ConsultaDetalleCMDTO dto) {
        return new PageReporteDetalleCM(reporteResumenDetalleCMRepository.obtenerReporte(dto, true));
    }

    @Override
    public byte[] obtenerReporteExport(ConsultaResumenDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return exportGenericService.exportarReporte(reporteResumenRepository.obtenerReporte(dto, false),
                "Reporte Financiero - Resumen", mapperResumen);
    }

    @Override
    public byte[] obtenerReporteDetalleCMExport(ConsultaDetalleCMDTO dto) {
        return exportGenericService.exportarReporte(reporteResumenDetalleCMRepository.obtenerReporte(dto, false),
                "Reporte Financiero - Resumen - Detalle CM", mapperResumen);
    }

    public String getMovimiento(ConsultaResumenDTO dto) {
        Map<String, Object> movimiento = new HashMap<>();
        movimiento.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        movimiento.put("idEstatusProyecto", dto.getIdEstatusProyecto());
        movimiento.put("idContratoVigente", dto.getIdContratoVigente());
        movimiento.put("nombreCortoContrato", dto.getNombreCortoContrato());
        movimiento.put("idDominiosTecnologicos", dto.getIdDominiosTecnologicos());
        movimiento.put("idConvenioColaboracion", dto.getIdConvenioColaboracion());


        return getMovimiento(movimiento);
    }
}
