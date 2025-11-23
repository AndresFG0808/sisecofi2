package com.sisecofi.catalogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatAdmonCentralRepository extends JpaRepository<CatAdmonCentral, Integer> {

	CatAdmonCentral findByAdministracionAndAcronimoAndPuestoAndCatAdmonGeneral(String administracion, String acronimo,
			String puesto, CatAdmonGeneral catAdmonGeneral);

	CatAdmonCentral findByAdministracionAndAcronimoAndPuestoAndCatAdmonGeneralIdAdmonGeneral(String administracion,
			String acronimo, String puesto, Integer idAdmonGeneral);

	@Query("select c from CatAdmonCentral c where c.administracion=:administracion and c.acronimo=:acronimo and c.puesto=:puesto and c.catAdmonGeneral.idAdmonGeneral=:idAdmonGeneral")
	CatAdmonCentral buscarRegistro(@Param("administracion") String administracion, @Param("acronimo") String acronimo,
			@Param("puesto") String puesto, @Param("idAdmonGeneral") Integer idAdmonGeneral);
	
	@Query("select c from CatAdmonCentral c where c.administracion=:administracion and c.acronimo=:acronimo and c.puesto=:puesto and c.catAdmonGeneral.idAdmonGeneral=:idAdmonGeneral and c.idAdmonCentral!=:idAdmonCentral")
	CatAdmonCentral buscarRegistroId(@Param("administracion") String administracion, @Param("acronimo") String acronimo,
			@Param("puesto") String puesto, @Param("idAdmonGeneral") Integer idAdmonGeneral,
			@Param("idAdmonCentral") Integer idAdmonCentral);

}
