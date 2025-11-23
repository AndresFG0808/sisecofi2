package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoDictamenDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteSeguimientoDictamen;
import com.sisecofi.reportedocumental.repository.financiero.ReporteSeguimientoDictamenRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.financiero.ReporteSeguimientoDictamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteSeguimientoDictamenServiceImpl extends ReporteFinancieroServiceBase implements ReporteSeguimientoDictamenService {
    private final ReporteSeguimientoDictamenRepository seguimientoDictamenRepository;
    private final ReportExportGenericService exportGenericService;
    private final PistaService microservicio;

    @Qualifier("mapperSeguimiento")
    private final MapperSeguimiento mapperSeguimiento;

    @Override
    public PageReporteSeguimientoDictamen obtenerReporte(ConsultaSeguimientoDictamenDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return new PageReporteSeguimientoDictamen(dto, seguimientoDictamenRepository.obtenerReporte(dto, true));
    }

    @Override
    public byte[] obtenerReporteExport(ConsultaSeguimientoDictamenDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return exportGenericService.exportarReporte(seguimientoDictamenRepository.obtenerReporte(dto, false),
                "Reporte Financiero - Seguimiento de Dictamen", mapperSeguimiento);
    }

    public String getMovimiento(ConsultaSeguimientoDictamenDTO dto) {
        Map<String, Object> movimiento = new HashMap<>();
        movimiento.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        movimiento.put("idContratoVigente", dto.getIdContratoVigente());
        movimiento.put("nombreCortoContrato", dto.getNombreCortoContrato());
        movimiento.put("idVerificadorContrato", dto.getIdVerificadorContrato());
        movimiento.put("idEstatusDictamen", dto.getListaIdEstatusDictamen());


        return getMovimiento(movimiento);
    }
}
