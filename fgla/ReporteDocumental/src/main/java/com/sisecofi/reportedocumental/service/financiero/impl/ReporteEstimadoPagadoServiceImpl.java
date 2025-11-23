package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstimadoPagadoDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteEstimadoPagado;
import com.sisecofi.reportedocumental.repository.financiero.ReporteEstimadoPagadoRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.financiero.ReporteEstimadoPagadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class ReporteEstimadoPagadoServiceImpl extends ReporteFinancieroServiceBase implements ReporteEstimadoPagadoService {
    private ReporteEstimadoPagadoRepository repository;
    private final ReportExportGenericService exportGenericService;
    private final PistaService microservicio;

    @Qualifier("mapperEstimadoPagado")
    private final MapperEstimadoPagado mapper;

    @Override
    public PageReporteEstimadoPagado obtenerReporte(ConsultaEstimadoPagadoDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return new PageReporteEstimadoPagado(dto, repository.obtenerReporte(dto, true));
    }

    @Override
    public byte[] obtenerReporteExport(ConsultaEstimadoPagadoDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return exportGenericService.exportarReporte(repository.obtenerReporte(dto, false), "Reporte Financiero - Estimado Pagado", mapper);
    }

    public String getMovimiento(ConsultaEstimadoPagadoDTO dto) {
        Map<String, Object> movimiento = new HashMap<>();
        movimiento.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        movimiento.put("idContratoVigente", dto.getIdContratoVigente());
        movimiento.put("nombreCortoContrato", dto.getNombreCortoContrato());
        movimiento.put("idConvenioColaboracion", dto.getIdConvenioColaboracion());


        return getMovimiento(movimiento);
    }
}
