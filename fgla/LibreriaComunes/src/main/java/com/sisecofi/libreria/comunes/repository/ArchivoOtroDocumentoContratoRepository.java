package com.sisecofi.libreria.comunes.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoContratoModel;


public interface ArchivoOtroDocumentoContratoRepository extends JpaRepository<ArchivoOtroDocumentoContratoModel, Integer> {

	List<Archivo> findByIdContratoAndEstatusTrue(Long idProyecto);

	Optional<Archivo> findByRuta(String ruta);
	
}
