package com.sisecofi.proyectos.repository.administradores;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;

public interface AdmonGeneralRepository extends JpaRepository<CatAdministradorGeneral, Integer> {
	
	@Query("SELECT c.idAdministradorGeneral " +
		       "FROM CatAdministradorGeneral c " +
		       "WHERE c.catAdmonGeneral.idAdmonGeneral = :idAdmonGeneral " +
		       "AND c.estatus = true")
		Optional<Integer> findIdAdministradorGeneralByCatAdmonGeneralId(@Param("idAdmonGeneral") Integer idAdmonGeneral);
	
	
	Optional<CatAdministradorGeneral> findByIdAdministradorGeneral(Integer idAdmonGeneral);

	
}
