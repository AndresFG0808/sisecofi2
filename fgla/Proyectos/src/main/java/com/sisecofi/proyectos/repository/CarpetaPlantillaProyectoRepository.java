package com.sisecofi.proyectos.repository;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.carpetas.CarpetaPlantillaProyectoModel;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarpetaPlantillaProyectoRepository extends JpaRepository<CarpetaPlantillaProyectoModel, Integer> {

	List<CarpetaPlantillaProyectoModel> findByNivelAndAsociacionesModel(int nivel, AsociacionesModel asociacion);
	
	List<CarpetaPlantillaProyectoModel> findByNivelAndAsociacionesModelIn(int nivel, List<AsociacionesModel> asociaciones);
	
	  List<CarpetaPlantillaProyectoModel> findByAsociacionesModelPlantillaVigenteModelCatFaseProyectoAndNivelAndCarpetaBaseEstatusAndAsociacionesModelInAndEstatusCarpetaTrueOrderByIdCarpetaPlantillaProyectoAsc(
		        CatFaseProyecto catFaseProyecto, int nivel, boolean estatus, List <AsociacionesModel> asociaciones);
	  
	  List<CarpetaPlantillaProyectoModel> findByNivelAndAsociacionesModelInAndEstatusCarpetaTrueAndCarpetaBaseEstatusTrue(
		         int nivel, List <AsociacionesModel> asociaciones);
	
	List<CarpetaPlantillaProyectoModel> findByAsociacionesModelInAndEstatusCarpetaTrue(List<AsociacionesModel> asociaciones);

}
