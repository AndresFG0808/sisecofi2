package com.sisecofi.libreria.comunes.repository;



import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArchivoPlantillaContratoRepository extends JpaRepository<ArchivoPlantillaContratoModel, Integer> {

	Optional<Archivo> findByRuta(String ruta);
	
	   @Query("SELECT COUNT(a) FROM ArchivoPlantillaContratoModel a WHERE a.cargado = :cargado AND a.carpetaPlantillaModel.contratoPlantilla.idContratoPlantilla = :idContratoPlantilla")
	    long countByCargadoAndIdContratoPlantilla(@Param("cargado") boolean cargado, @Param("idContratoPlantilla") Long idContratoPlantilla);

	   @Query("SELECT a FROM ArchivoPlantillaContratoModel a WHERE a.carpetaPlantillaModel.contratoPlantilla IN :asociaciones")
	   List<ArchivoPlantillaContratoModel> findAllByContratoPlantillaIn(@Param("asociaciones") List<ContratoPlantilla> asociaciones);
 
	   List<ArchivoPlantillaContratoModel> findByCarpetaPlantillaModelContratoPlantillaContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(Long idPoryecto);
}
