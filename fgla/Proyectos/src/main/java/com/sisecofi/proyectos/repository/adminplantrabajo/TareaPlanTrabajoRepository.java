package com.sisecofi.proyectos.repository.adminplantrabajo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.model.proyectos.adminplantrabajo.PlanTrabajoModel;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajoModel;

import feign.Param;

public interface TareaPlanTrabajoRepository extends JpaRepository<TareaPlanTrabajoModel, Long> {

    @Query("SELECT t FROM TareaPlanTrabajoModel t " +
            "JOIN t.planTrabajoModel p " +
            "JOIN p.proyectoModel pr " +
            "WHERE pr.idProyecto = :idProyecto " +
            "ORDER BY t.idTareaPlanTrabajo ASC")
    List<TareaPlanTrabajoModel> findAllByIdProyecto(@Param("idProyecto") Long idProyecto);

    List<TareaPlanTrabajoModel> findByPlanTrabajoModel(PlanTrabajoModel planTrabajoModel);

    List<TareaPlanTrabajoModel> findByPlanTrabajoModelProyectoModelIdProyecto(Long idProyecto);

    void deleteByPlanTrabajoModel(PlanTrabajoModel planTrabajoModel);

    TareaPlanTrabajoModel findSingleByPlanTrabajoModelProyectoModelIdProyecto(Long idProyecto);

    Optional<TareaPlanTrabajoModel> findByIdTarea(Integer idTarea);

    @Query("FROM TareaPlanTrabajoModel p WHERE p.activo = TRUE")
    List<TareaPlanTrabajoModel> obtenerResultadosCalculo();

}
