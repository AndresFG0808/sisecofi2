package com.sisecofi.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdministradorCentralRepository extends JpaRepository<CatAdministradorCentral, Integer> {

	List<CatAdministradorCentral> findByEstatus(boolean b);

	@Query("update CatAdministradorCentral set estatus=:estatus where id=:id")
	@Modifying
	void borrarLogico(@Param("estatus") boolean estatus, @Param("id") Integer id);

	@Query("update CatAdministradorCentral set estatus=:estatus")
	@Modifying
	void borrarLogicoTodos(@Param("estatus") boolean estatus);

	CatAdministradorCentral findByEstatusAndCatAdmonCentralIdAdmonCentral(boolean b, Integer idAdmonCentral);

	List<CatAdministradorCentral> findByCatAdmonCentralIdAdmonCentral(int id);
	
	List<CatAdministradorCentral> findByCatAdmonCentralIdAdmonCentralOrderByIdAdministradorCentralAsc(int id);

}
