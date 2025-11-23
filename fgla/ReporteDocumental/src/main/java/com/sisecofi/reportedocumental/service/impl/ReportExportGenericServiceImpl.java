package com.sisecofi.reportedocumental.service.impl;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.dinamico.Mapper;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.ReporteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ayuso2104@gmail.com
 */

@Service
@RequiredArgsConstructor
public class ReportExportGenericServiceImpl implements ReportExportGenericService {

    private final ReporteGenericConsumer consumer;

    @Override
    public byte[] exportarReporte(PageGeneric generic, String nombrePestanna, Mapper mapper) {
        if (!generic.getContent().isEmpty()) {
            consumer.inializar(nombrePestanna);

            if (generic.getGrupoEtiquetas() != null && !generic.getGrupoEtiquetas().isEmpty()) {
                consumer.agregarCabeceras(generic.getEtiquetas(), generic.getGrupoEtiquetas());
            } else {
                consumer.agregarCabeceras(generic.getEtiquetas());
            }

            consumer.setMapper(mapper);
            generic.getContent().stream().forEach(consumer);
            return consumer.cerrarBytes();
        }
        throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_EXCEL);
    }

}
