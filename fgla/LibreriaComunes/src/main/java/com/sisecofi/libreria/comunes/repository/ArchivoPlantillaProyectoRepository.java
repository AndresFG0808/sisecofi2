package com.sisecofi.libreria.comunes.repository;


import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArchivoPlantillaProyectoRepository extends JpaRepository<ArchivoPlantillaProyectoModel, Integer> {

	Optional<Archivo> findByRuta(String ruta);
	
	   @Query("SELECT COUNT(a) FROM ArchivoPlantillaProyectoModel a WHERE a.cargado = :cargado AND a.carpetaPlantillaModel.asociacionesModel.idAsociacion = :idAsociacion")
	    long countByCargadoAndIdAsociacion(@Param("cargado") boolean cargado, @Param("idAsociacion") Long idAsociacion);

	   @Query("SELECT a FROM ArchivoPlantillaProyectoModel a WHERE a.carpetaPlantillaModel.asociacionesModel IN :asociaciones")
	   List<ArchivoPlantillaProyectoModel> findAllByAsociacionesModelIn(@Param("asociaciones") List<AsociacionesModel> asociaciones);
   
	   List<Archivo> findByCarpetaPlantillaModelAsociacionesModelIdProyectoAndArchivoBaseEstatusTrue(Long idProyecto);

	   @Query("select a.ruta from ArchivoPlantillaProyectoModel a where a.id=:id")
		String buscarRuta(@Param("id") Integer id);
	   
	   Optional<List<ArchivoPlantillaProyectoModel>> findByCarpetaPlantillaModelAsociacionesModelEstatusAsociacionTrueAndCarpetaPlantillaModelAsociacionesModelIdProyectoAndArchivoBaseEstatusTrueAndCarpetaPlantillaModelCarpetaBaseEstatusTrueAndObligatorioTrueAndNoAplicaFalse(Long idProyecto);
}
