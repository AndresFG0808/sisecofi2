package com.sisecofi.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.proyectos.FichaAdmonCentral;

/**
 * Repositorio para actualizar las fichas técnicas cuando se modifica un administrador central.
 * Este repositorio permite mantener sincronizadas las fichas técnicas con los cambios en el catálogo.
 */
public interface FichaAdmonCentralRepository extends JpaRepository<FichaAdmonCentral, Long> {
	
	/**
	 * Encuentra todas las fichas técnicas que tienen asignado un administrador central específico.
	 * 
	 * @param idAdministradorCentral ID del administrador central
	 * @return Lista de fichas técnicas con ese administrador
	 */
	List<FichaAdmonCentral> findByIdAdministradorCentral(Integer idAdministradorCentral);
	
	/**
	 * Encuentra todas las fichas técnicas que pertenecen a una administración central específica.
	 * 
	 * @param idAdmonCentral ID de la administración central
	 * @return Lista de fichas técnicas de esa administración
	 */
	List<FichaAdmonCentral> findByIdAdmonCentral(Integer idAdmonCentral);
	
	/**
	 * Actualiza el administrador central en todas las fichas de una administración específica
	 * que tengan un administrador activo vigente.
	 * 
	 * @param idAdmonCentral ID de la administración central
	 * @param nuevoIdAdministradorCentral ID del nuevo administrador
	 */
	@Modifying
	@Query(value = "UPDATE sisecofi.sscfr_ficha_tecnica_admon_central " +
	               "SET id_administrador_central = :nuevoIdAdministrador " +
	               "WHERE id_admon_central = :idAdmonCentral " +
	               "AND estatus = true", 
	       nativeQuery = true)
	void actualizarAdministradorEnFichasActivas(
		@Param("idAdmonCentral") Integer idAdmonCentral,
		@Param("nuevoIdAdministrador") Integer nuevoIdAdministradorCentral
	);
}
