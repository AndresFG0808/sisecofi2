package com.sisecofi.proyectos.repository.administradores;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;

public interface AdmonCentralRepository extends JpaRepository<CatAdministradorCentral, Integer> {
	
	@Query("SELECT c.idAdministradorCentral " +
		       "FROM CatAdministradorCentral c " +
		       "WHERE c.catAdmonCentral.idAdmonCentral = :idAdmonCentral " +
		       "AND c.estatus = true")
		Optional<Integer> findIdAdministradorCentralByCatAdmonCentralId(@Param("idAdmonCentral") Integer idAdmonCentral);
	
	Optional<CatAdministradorCentral> findByIdAdministradorCentral(Integer idAdmonCentral);

	
}
