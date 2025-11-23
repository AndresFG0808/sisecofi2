package com.sisecofi.libreria.comunes.repository.cierre;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;


public interface ArchivoOtroDocumentoFaseDictamenRepository extends JpaRepository<ArchivoOtroDocumentoFaseDictamenModel, Integer> {
	
	List<Archivo> findByIdDictamenAndEstatusTrueOrderById(Long idDictamen);
	
	List<ArchivoOtroDocumentoFaseDictamenModel> findByIdDictamenAndEstatusTrue(Long idDictamen);
	
	@Query("SELECT a FROM ArchivoOtroDocumentoFaseDictamenModel a WHERE a.dictamen.contratoModel.proyecto.idProyecto = :idProyecto AND a.estatus = true")
	List<Archivo> findByDictamen_ContratoModel_idProyectoAndEstatusTrue(@Param("idProyecto") Long idProyecto);
	
	Optional<Archivo> findByRuta(String ruta);
	
}
