package com.sisecofi.contratos.repository.administradores;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradoresRepository extends JpaRepository<CatAdministradorCentral, Integer> {

	List<CatAdministradorCentral> findByCatAdmonCentralIdAdmonCentralAndEstatusTrue(Integer idAdmonCentral);
	
	List<CatAdministradorCentral> findByCatAdmonCentralIdAdmonCentral(Integer idCentral);
}
