package com.sisecofi.proyectos.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.proyectos.FichaAdmonCentral;


public interface FichaAdmonCentralRepository extends JpaRepository<FichaAdmonCentral, Long> {
	
List<FichaAdmonCentral> findByIdFichaTecnicaAndEstatusTrue(Long idFichaTecnica);

Optional <FichaAdmonCentral> findByIdFichaTecnicaAndIdAdmonCentral(Long idFichaTecnica, Integer admon);

void deleteByIdFichaTecnica(Long idFichaTecnica);
	
}
