package com.sisecofi.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdministradorAdministracionRepository extends JpaRepository<CatAdministradorAdministracion, Integer> {

	@Query("update CatAdministradorAdministracion set estatus=:estatus where id=:id")
	@Modifying
	void borrarLogico(@Param("estatus") boolean b, @Param("id") Integer id);

	@Query("update CatAdministradorAdministracion set estatus=:estatus ")
	@Modifying
	void borrarLogicoTodos(@Param("estatus") boolean b);

	CatAdministradorAdministracion findByEstatusAndCatAdministracionIdAdministracion(boolean b,
			Integer idAdministracion);

	List<CatAdministradorAdministracion> findByCatAdministracionIdAdministracion(int id);
	
	List<CatAdministradorAdministracion> findByCatAdministracionIdAdministracionOrderByIdAdministradorAdministracionAsc(int id);

}
