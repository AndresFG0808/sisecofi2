package com.sisecofi.proyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;

import java.util.List;
import java.util.Optional;

public interface AsociacionComiteRepository extends JpaRepository<AsociasionComitePlantillaModel, Integer> {

    Optional<AsociasionComitePlantillaModel> findByIdComiteProyectoAndEstatusTrue(Integer idComiteProyecto);

    Optional<AsociasionComitePlantillaModel> findByIdComiteProyecto(Integer idComiteProyecto);

    Optional<List<AsociasionComitePlantillaModel>> findByIdPlantillaVigenteAndEstatusTrue(Integer idPlantillaVigente);

    Optional<AsociasionComitePlantillaModel> findByIdAsociacionComitePlantilla(Integer idAsociacionComitePlantilla);
}
