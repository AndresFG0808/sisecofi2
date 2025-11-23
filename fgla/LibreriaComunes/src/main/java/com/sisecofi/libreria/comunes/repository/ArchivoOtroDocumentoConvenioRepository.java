package com.sisecofi.libreria.comunes.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoConvenioModel;


public interface ArchivoOtroDocumentoConvenioRepository extends JpaRepository<ArchivoOtroDocumentoConvenioModel, Integer> {

	List<Archivo> findByIdConvenioModificatorioAndEstatusTrue(Long idConvenio);

	Optional<Archivo> findByRuta(String ruta);
	
}
