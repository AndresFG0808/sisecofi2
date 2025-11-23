package com.sisecofi.libreria.comunes.repository.cierre;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoFaseReintegroModel;

public interface ArchivoOtroDocumentoFaseReintegroRepository extends JpaRepository<ArchivoOtroDocumentoFaseReintegroModel, Integer> {
	
	List<Archivo> findByIdReintegrosAsociadosAndEstatusTrue( Long idReintegro);
	
	@Query("SELECT a FROM ArchivoOtroDocumentoFaseReintegroModel a WHERE a.reintegro.contratoModel.proyecto.idProyecto = :idProyecto AND a.estatus = true")
	List<Archivo> findByReintegrosAsociadosModel_ContratoModel_idProyectoAndEstatusTrue(@Param("idProyecto") Long idProyecto);
	
	Optional<Archivo> findByRuta(String ruta);
	
}
