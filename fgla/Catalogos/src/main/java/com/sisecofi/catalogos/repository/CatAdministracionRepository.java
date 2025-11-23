package com.sisecofi.catalogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatAdministracionRepository extends JpaRepository<CatAdministracion, Integer> {

	CatAdministracion findByAdministracionAndAcronimoAndPuestoAndCatAdmonCentralIdAdmonCentral(String administracion,
			String acronimo, String puesto, Integer idAdmonCentral);

	@Query("select c from CatAdministracion c where c.administracion=:administracion and c.acronimo=:acronimo and c.puesto=:puesto and c.catAdmonCentral.idAdmonCentral=:idAdmonCentral")
	CatAdministracion buscarRegistro(@Param("administracion") String administracion, @Param("acronimo") String acronimo,
			@Param("puesto") String puesto, @Param("idAdmonCentral") Integer idAdmonCentral);

	@Query("select c from CatAdministracion c where c.administracion=:administracion and c.acronimo=:acronimo and c.puesto=:puesto and c.catAdmonCentral.idAdmonCentral=:idAdmonCentral and c.idAdministracion!=:idAdministracion")
	CatAdministracion buscarRegistroId(@Param("administracion") String administracion, @Param("acronimo") String acronimo,
			@Param("puesto") String puesto, @Param("idAdmonCentral") Integer idAdmonCentral,
			@Param("idAdministracion") Integer idAdministracion);

}
