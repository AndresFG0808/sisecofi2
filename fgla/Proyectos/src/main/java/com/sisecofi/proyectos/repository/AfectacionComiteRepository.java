package com.sisecofi.proyectos.repository;

import com.sisecofi.proyectos.model.AfectacionComiteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AfectacionComiteRepository extends JpaRepository<AfectacionComiteModel, Integer> {

    @Query("SELECT idAfectacion FROM AfectacionComiteModel WHERE idComiteProyecto = :idComiteProyecto")
    List<Integer> findAllIdsByIdComiteProyecto(Integer idComiteProyecto);

    List<AfectacionComiteModel> findAllByIdComiteProyecto(Integer idComiteProyecto);
}
