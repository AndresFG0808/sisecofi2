package com.sisecofi.proyectos.repository.adminplantrabajo;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.model.proyectos.adminplantrabajo.PlanTrabajoModel;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PlanTrabajoRepository extends JpaRepository<PlanTrabajoModel, Long> {

    Optional<PlanTrabajoModel> findByProyectoModelIdProyecto(Long idProyecto);

    PlanTrabajoModel findFirstByProyectoModelIdProyecto(Long idProyecto);

    @Query(value = "SELECT sp.id_proyecto, stpt.fecha_inicio_planeado, stpt.fecha_fin_planeado, stpt.porcentaje_planeado "
            +
            "FROM sscft_proyecto sp " +
            "JOIN sscft_plan_trabajo spt ON sp.id_proyecto = spt.id_proyecto " +
            "JOIN sscft_tarea_plan_trabajo stpt ON spt.id_plan = stpt.id_plan " +
            "JOIN sscfc_estatus_proyecto sep ON sp.id_estatus_proyecto = sep.id_estatus_proyecto " +
            "WHERE sep.id_estatus_proyecto NOT IN (5, 6) " +
            "AND sp.estatus = true " +
            "AND stpt.id_tarea = 0", nativeQuery = true)
    List<Object[]> obtenerPorcentajePlaneado();

    boolean existsByProyectoModelIdProyecto(Long idProyecto);



}
        