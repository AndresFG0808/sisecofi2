package com.sisecofi.proyectos.repository.cierre;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.proyectos.model.cierre.CierreModel;

public interface CierreRepository extends JpaRepository<CierreModel, Long> {
	
	Optional<CierreModel> findByIdCierreAndEstatusTrue(Long idCierre);
	Optional<CierreModel> findByIdProyecto(Long idProyecto);
	Optional<CierreModel> findByIdProyectoAndEstatusTrue(Long idProyecto);
}
