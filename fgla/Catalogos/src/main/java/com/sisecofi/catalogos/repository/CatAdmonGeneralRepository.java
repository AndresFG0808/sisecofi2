package com.sisecofi.catalogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatAdmonGeneralRepository extends JpaRepository<CatAdmonGeneral, Integer> {

	CatAdmonGeneral findByAdministracionAndAcronimoAndPuesto(String administracion, String acronimo, String puesto);

	@Query("select c from CatAdmonGeneral c where c.administracion=:administracion and c.acronimo=:acronimo and c.puesto=:puesto and c.idAdmonGeneral!=:idAdmonGeneral")
	CatAdmonGeneral findByAdministracionAndAcronimoAndPuestoId(@Param("administracion") String administracion,@Param("acronimo") String acronimo,@Param("puesto") String puesto,
			@Param("idAdmonGeneral") Integer idAdmonGeneral);

}
