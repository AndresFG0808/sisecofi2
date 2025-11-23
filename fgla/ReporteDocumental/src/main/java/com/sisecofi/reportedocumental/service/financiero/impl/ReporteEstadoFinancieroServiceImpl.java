package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstadoFinancieroDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageEstadoFinanciero;
import com.sisecofi.reportedocumental.repository.financiero.ReporteEstadoFinancieroRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.financiero.ReporteEstadoFinancieroService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReporteEstadoFinancieroServiceImpl extends ReporteFinancieroServiceBase implements ReporteEstadoFinancieroService {
    private ReporteEstadoFinancieroRepository repository;
    private final ReportExportGenericService exportGenericService;
    private final PistaService microservicio;

    @Qualifier("mapperEstadoFinanciero")
    private final MapperEstadoFinanciero mapper;

    @Override
    public PageEstadoFinanciero obtenerReporte(ConsultaEstadoFinancieroDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return new PageEstadoFinanciero(dto, repository.obtenerReporte(dto, true));
    }

    @Override
    public byte[] obtenerReporteExport(ConsultaEstadoFinancieroDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return exportGenericService.exportarReporte(repository.obtenerReporte(dto, false), "Reporte Financiero - Estado Financiero", mapper);
    }

    public String getMovimiento(ConsultaEstadoFinancieroDTO dto) {
        Map<String, Object> movimiento = new HashMap<>();
        movimiento.put("idEstatusProyecto", dto.getIdEstatusProyecto());
        movimiento.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        movimiento.put("idContratoVigente", dto.getIdContratoVigente());
        movimiento.put("nombreCortoContrato", dto.getNombreCortoContrato());
        movimiento.put("idDominiosTecnologicos", dto.getIdDominiosTecnologicos());
        movimiento.put("periodoInicio", dto.getPeriodoInicio());
        movimiento.put("periodoFin", dto.getPeriodoFin());

        return getMovimiento(movimiento);
    }
}
