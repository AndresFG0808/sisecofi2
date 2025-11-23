package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoLineaServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageSeguimientoLineaServicio;
import com.sisecofi.reportedocumental.repository.financiero.ReporteSeguimientoLineaServicioRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.financiero.ReporteSeguimientoLineaServicioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReporteSeguimientoLineaServicioServiceImpl extends ReporteFinancieroServiceBase implements ReporteSeguimientoLineaServicioService {
    private final ReporteSeguimientoLineaServicioRepository repository;
    private final ReportExportGenericService exportGenericService;
    private final PistaService microservicio;

    @Qualifier("mapperSeguimientoLineaServicio")
    private final MapperSeguimientoLineaServicio mapperSeguimientoLineaServicio;

    @Override
    public PageSeguimientoLineaServicio obtenerReporte(ConsultaSeguimientoLineaServicioDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return new PageSeguimientoLineaServicio(dto, repository.obtenerReporte(dto, true));
    }

    @Override
    public byte[] obtenerReporteExport(ConsultaSeguimientoLineaServicioDTO dto) {
        microservicio.guardarPista(ModuloPista.REPORTE_FINANCIERO.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(), TipoSeccionPista.REPORTE_FINANCIERO.getIdSeccionPista(), getMovimiento(dto));
        return exportGenericService.exportarReporte(
                repository.obtenerReporte(dto, false),
                "Reporte Financiero - Seguimiento Línea de Servicio",
                mapperSeguimientoLineaServicio
        );
    }

    public String getMovimiento(ConsultaSeguimientoLineaServicioDTO dto) {
        Map<String, Object> movimiento = new HashMap<>();
        movimiento.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
        movimiento.put("idContratoVigente", dto.getIdContratoVigente());
        movimiento.put("nombreCortoContrato", dto.getNombreCortoContrato());
        movimiento.put("idVerificadorContrato", dto.getIdConvenioColaboracion());
        movimiento.put("idServicioContrato", dto.getListaIdServicioContrato());
        movimiento.put("periodoInicio", dto.getPeriodoInicio());
        movimiento.put("periodoFin", dto.getPeriodoFin());
        movimiento.put("idEstatusVolumetria", dto.getListaIdEstatusVolumetria());

        return getMovimiento(movimiento);
    }
}
