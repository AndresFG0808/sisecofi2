package com.sisecofi.libreria.comunes.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoFaseModel;


public interface ArchivoOtroDocumentoFaseRepository extends JpaRepository<ArchivoOtroDocumentoFaseModel, Integer> {
	List<Archivo> findByIdFaseProyectoAndIdProyectoAndEstatus(Integer idFase, long idProyecto, boolean estatus);
	
	@Query("SELECT a FROM ArchivoOtroDocumentoFaseModel a WHERE a.idProyecto = :idProyecto AND a.estatus = true")
	List<Archivo> findByIdProyectoAndEstatusTrue(@Param("idProyecto") Long idProyecto);
	
	Optional<Archivo> findByRuta(String ruta);

	@Query("select a.ruta from ArchivoOtroDocumentoFaseModel a where a.id=:id")
	String buscarRuta(@Param("id") Integer id);
	
}
