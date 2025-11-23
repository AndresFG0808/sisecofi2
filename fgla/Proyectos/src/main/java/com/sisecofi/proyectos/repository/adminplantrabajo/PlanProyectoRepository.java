package com.sisecofi.proyectos.repository.adminplantrabajo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

@Repository
public interface PlanProyectoRepository extends JpaRepository <ProyectoModel, Long>{


    Optional<ProyectoModel> findByIdProyectoAndEstatus(Long id, boolean estatus);


}
