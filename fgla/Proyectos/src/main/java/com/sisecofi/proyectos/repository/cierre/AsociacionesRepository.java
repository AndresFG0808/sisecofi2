package com.sisecofi.proyectos.repository.cierre;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;

public interface AsociacionesRepository extends JpaRepository<AsociacionesModel, Long> {
	
	Boolean existsByidProyecto(Long idProyecto);

}
