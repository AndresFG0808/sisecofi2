package com.sisecofi.admindevengados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sisecofi.admindevengados.model.SoporteDocumentalModel;

public interface SoporteDocumentalRepository extends JpaRepository<SoporteDocumentalModel, Long> {

	SoporteDocumentalModel findByIdDictamen(Long idDictamen);
	boolean existsByIdDictamen(Long idDictamen);
}
