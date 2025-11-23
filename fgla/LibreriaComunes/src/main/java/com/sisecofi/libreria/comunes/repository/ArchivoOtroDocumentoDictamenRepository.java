package com.sisecofi.libreria.comunes.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoDictamenModel;


public interface ArchivoOtroDocumentoDictamenRepository extends JpaRepository<ArchivoOtroDocumentoDictamenModel, Integer> {

	List<Archivo> findByIdDictamenAndEstatusTrue(Long idDictamen);
	
	List<ArchivoOtroDocumentoDictamenModel> findByIdDictamenAndEstatusTrueOrderById(Long idDictamen);

	Optional<Archivo> findByRuta(String ruta);
	
}
