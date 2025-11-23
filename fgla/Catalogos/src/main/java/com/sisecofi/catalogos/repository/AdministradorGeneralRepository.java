package com.sisecofi.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdministradorGeneralRepository extends JpaRepository<CatAdministradorGeneral, Integer> {

	List<CatAdministradorCentral> findByEstatus(boolean b);

	@Query("update CatAdministradorGeneral set estatus=:estatus where id=:id")
	@Modifying
	void borrarLogico(@Param("estatus") boolean b, @Param("id") Integer id);

	@Query("update CatAdministradorGeneral set estatus=:estatus ")
	@Modifying
	void borrarLogicoTodos(@Param("estatus") boolean b);

	List<CatAdministradorGeneral> findByCatAdmonGeneralIdAdmonGeneral(int id);
	
	List<CatAdministradorGeneral> findByCatAdmonGeneralIdAdmonGeneralOrderByIdAdministradorGeneralAsc(int id);
}
