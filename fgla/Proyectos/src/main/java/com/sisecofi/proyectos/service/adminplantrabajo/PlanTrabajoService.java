package com.sisecofi.proyectos.service.adminplantrabajo;

import java.time.LocalDateTime;
import java.util.List;

import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionTareaRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;

public interface PlanTrabajoService {

    List<ListaTareas> tablaPlanTrabajo(Long idProyecto);

    LocalDateTime obtenerUltimaModificacion(Long idProyecto);

    List<TareaPlanTrabajoDto> calcularPorcentajes(Long idProyecto, Integer idTarea, Integer completadoRequest);

     List<TareaPlanTrabajoDto>modificarTareaTemporalesLista(Long idProyecto, List<ModificacionTareaRequest> modificaciones);   

   void cancelarCambios(Long idProyecto);
}
