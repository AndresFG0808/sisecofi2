package com.sisecofi.proyectos.repository.administradores;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;

public interface AdministracionRepository extends JpaRepository<CatAdministradorAdministracion, Integer> {
	
	@Query("SELECT c.idAdministradorAdministracion " +
		       "FROM CatAdministradorAdministracion c " +
		       "WHERE c.catAdministracion.idAdministracion = :idAdministracion " +
		       "AND c.estatus = true")
		Optional<Integer> findIdAdministradorAdministracionByCatAdministracionId(@Param("idAdministracion") Integer idAdministracion);

	Optional<CatAdministradorAdministracion> findByIdAdministradorAdministracion(Integer idAdmon);
	
}
