package com.sisecofi.proyectos.service.adminplantrabajo.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.adminplantrabajo.PlanTrabajoRespaldoService;
import com.sisecofi.proyectos.service.adminplantrabajo.ReportePlanTrabajoService;
import com.sisecofi.proyectos.util.adminplantrabajo.constantes.Constantes;
import com.sisecofi.proyectos.util.consumer.adminplantrabajo.ReportePlanTrabajoConsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportePlanTrabajoServiceImpl implements ReportePlanTrabajoService {

    private final ReportePlanTrabajoConsumer reportePlanTrabajoConsumer;
    private final PlanTrabajoRespaldoService planTrabajoService;
    private final PistaService pistaService;

    @Override
    public byte[] obtenerReportePlanTrabajo(Long idProyecto) {

        List<ListaTareas> lista = planTrabajoService.tablaPlanTrabajo(idProyecto);
        reportePlanTrabajoConsumer.inializar("Plan trabajo cargado");
        reportePlanTrabajoConsumer.agregarCabeceras(Constantes.TITULOS_PLAN_TRABAJO);
        lista.stream().forEach(reportePlanTrabajoConsumer);

        StringBuilder builder = new StringBuilder();

        builder.append("Id Proyecto: ").append(idProyecto).append(" - Plan de trabajo - Carga masiva");

        pistaService.guardarPista(ModuloPista.PROYECTOS.getId(),
                TipoMovPista.IMPRIME_REGISTRO.getId(),
                TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),
                builder.toString(),
                Optional.empty());

        return reportePlanTrabajoConsumer.cerrarBytes();

    }

}
