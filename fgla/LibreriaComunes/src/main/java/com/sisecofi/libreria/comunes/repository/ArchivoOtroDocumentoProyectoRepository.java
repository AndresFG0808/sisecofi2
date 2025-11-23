package com.sisecofi.libreria.comunes.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoProyectoModel;

import feign.Param;


public interface ArchivoOtroDocumentoProyectoRepository extends JpaRepository<ArchivoOtroDocumentoProyectoModel, Integer> {

	List<Archivo> findByIdProyectoAndEstatus(Long idProyecto, boolean estatus);

	Optional<Archivo> findByRuta(String ruta);
	
	@Query("select a.ruta from ArchivoOtroDocumentoProyectoModel a where a.id=:id")
	String buscarRuta(@Param("id") Integer id);
	
}
