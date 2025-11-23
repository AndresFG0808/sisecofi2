package com.sisecofi.proyectos.service.adminplantrabajo;

import java.time.LocalDateTime;
import java.util.List;

import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionCompletadoRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionTareaRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;

public interface PlanTrabajoRespaldoService {

    List<ListaTareas> tablaPlanTrabajo(Long idProyecto);
    LocalDateTime obtenerUltimaModificacion(Long idProyecto);
    List<TareaPlanTrabajoDto> modificarTareasOriginales(Long idProyecto, List<ModificacionTareaRequest> modificaciones);      
    List<TareaPlanTrabajoDto> calcularPorcentajeListaTareas(Long idProyecto, List<ModificacionCompletadoRequest> modificaciones);
}