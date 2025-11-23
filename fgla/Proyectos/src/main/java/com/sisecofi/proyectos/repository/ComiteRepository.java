package com.sisecofi.proyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;

import java.util.List;
import java.util.Optional;


public interface ComiteRepository  extends JpaRepository<ComiteProyectoModel, Integer>,
        JpaSpecificationExecutor<ComiteProyectoModel> {

    List<ComiteProyectoModel> findByEstatusTrue();

    Optional<ComiteProyectoModel> findByIdComiteProyecto(Integer idComiteProyecto);

    Optional<ComiteProyectoModel> findByIdProyecto(Integer idProyecto);

    Optional<List<ComiteProyectoModel>> findByIdProyectoAndEstatusTrue(Integer idProyecto);
    
   List<ComiteProyectoModel> findByIdProyectoAndEstatusTrue(Long idProyecto);

    List<ComiteProyectoModel> findAllByIdComite(Integer idComite);
}
