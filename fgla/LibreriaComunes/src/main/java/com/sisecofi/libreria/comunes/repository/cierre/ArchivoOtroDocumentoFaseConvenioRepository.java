package com.sisecofi.libreria.comunes.repository.cierre;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseConvenioModel;


public interface ArchivoOtroDocumentoFaseConvenioRepository extends JpaRepository<ArchivoOtroDocumentoFaseConvenioModel, Integer> {
	
	List<Archivo> findByIdConvenioModificatorioAndEstatusTrue( Long idConvenio);
	
	@Query("SELECT a FROM ArchivoOtroDocumentoFaseConvenioModel a WHERE a.convenio.contratoModel.proyecto.idProyecto = :idProyecto AND a.estatus = true")
	List<Archivo> findByConvenioModificatorioModel_ContratoModel_idProyectoAndEstatusTrue(@Param("idProyecto") Long idProyecto);
	
	Optional<Archivo> findByRuta(String ruta);
	
}
