package com.sisecofi.libreria.comunes.repository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArchivoPlantillaConvenioRepository extends JpaRepository<ArchivoPlantillaConvenioModel, Integer> {

	Optional<Archivo> findByRuta(String ruta);
	
	   @Query("SELECT COUNT(a) FROM ArchivoPlantillaConvenioModel a WHERE a.cargado = :cargado AND a.carpetaPlantillaConvenioModel.convenioPlantilla.idConvenioPlantilla = :idConvenioPlantilla")
	    long countByCargadoAndIdContratoPlantilla(@Param("cargado") boolean cargado, @Param("idConvenioPlantilla") Long idConvenioPlantilla);

	   @Query("SELECT a FROM ArchivoPlantillaConvenioModel a WHERE a.carpetaPlantillaConvenioModel.convenioPlantilla IN :asociaciones")
	   List<ArchivoPlantillaConvenioModel> findAllByConvenioPlantillaIn(@Param("asociaciones") List<ConvenioPlantilla> asociaciones);
	   
	   List<ArchivoPlantillaConvenioModel> findByCarpetaPlantillaConvenioModelConvenioPlantillaConvenioContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(Long idProyecto);
   
}
 