package com.sisecofi.libreria.comunes.repository.cierre;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseContratoModel;


public interface ArchivoOtroDocumentoFaseContratoRepository extends JpaRepository<ArchivoOtroDocumentoFaseContratoModel, Integer> {
	
	List<Archivo> findByIdContratoAndEstatusTrue( Long idContrato);
	
	@Query("SELECT a FROM ArchivoOtroDocumentoFaseContratoModel a WHERE a.contratoModel.proyecto.idProyecto = :idProyecto AND a.estatus = true")
	List<Archivo> findByContratoModel_idProyectoAndEstatusTrue(@Param("idProyecto") Long idProyecto);
	
	Optional<Archivo> findByRuta(String ruta);
	
}
