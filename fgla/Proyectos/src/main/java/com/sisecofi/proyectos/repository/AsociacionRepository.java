package com.sisecofi.proyectos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;

public interface AsociacionRepository extends JpaRepository<AsociacionesModel, Long> {
	
	Optional<AsociacionesModel> findByIdAsociacionAndEstatusAsociacion (Long idAsociacion, boolean estatus);
	
	Optional<AsociacionesModel> findByIdProyectoAndIdPlantillaVigenteAndEstatusAsociacion (Long idProyecto, Integer idPlantilla, boolean estatus);
	
	List <AsociacionesModel> findByIdProyectoAndEstatusAsociacionOrderByOrdenAsc (Long idProyecto, boolean estatus);
	
	List <AsociacionesModel> findByIdProyectoAndEstatusAsociacionTrue(Long idProyecto);
	
	List <AsociacionesModel> findByIdProyectoAndEstatusAsociacionTrueOrderByIdAsociacionAsc(Long idProyecto);
	
	AsociacionesModel findByIdAsociacion(Long idAsociacion);
	 
	boolean existsByIdProyectoAndIdPlantillaVigenteAndEstatusAsociacion(Long idProyecto, Integer idPlantilla, boolean estatus);
	
	boolean existsByIdProyectoAndEstatusAsociacionAndPlantillaVigenteModelCatFaseProyectoNombre(Long idProyecto, boolean estatus, String nombre);
	
	long countByIdProyectoAndIdPlantillaVigenteAndEstatusAsociacion(Long idProyecto, Integer idPlantilla, boolean estatus);
	
	long countByIdProyectoAndEstatusAsociacionAndPlantillaVigenteModelCatFaseProyectoNombre(Long idProyecto, boolean estatus, String nombre);
	
	List <AsociacionesModel> findByEstatusAsociacionTrue();

}
