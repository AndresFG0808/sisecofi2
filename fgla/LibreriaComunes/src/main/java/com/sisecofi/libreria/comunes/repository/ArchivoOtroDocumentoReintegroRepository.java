package com.sisecofi.libreria.comunes.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoReintegroModel;


public interface ArchivoOtroDocumentoReintegroRepository extends JpaRepository<ArchivoOtroDocumentoReintegroModel, Integer> {

	List<Archivo> findByIdContratoAndEstatusTrue(Long idContrato);

	Optional<Archivo> findByRuta(String ruta);
	
}
