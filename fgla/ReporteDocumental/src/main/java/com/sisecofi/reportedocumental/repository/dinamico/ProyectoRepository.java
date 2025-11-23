package com.sisecofi.reportedocumental.repository.dinamico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

/**
 * @author ayuso2104@gmail.com
 */

public interface ProyectoRepository extends JpaRepository<ProyectoModel, Long> {

	@Query(nativeQuery = true)
	List<ProyectoNombreDto> findByIdEstatusProyecto(List<Integer> estatusProyecto);

	@Query(nativeQuery = true)
	List<ProyectoNombreDto> findByIdProyecto(List<Long> idProyecto);

	@Query(value = "SELECT p.id_proyecto, p.nombre_corto FROM sisecofi.sscft_proyecto p WHERE p.id_estatus_proyecto IN (:estatusProyecto) ORDER BY p.nombre_corto ASC", nativeQuery = true)
	List<Object[]> findByIdEstatusProyectoOrdenado(List<Integer> estatusProyecto);

	@Query(value = "SELECT p.id_proyecto, p.nombre_corto FROM sisecofi.sscft_proyecto p order by p.nombre_corto asc", nativeQuery = true)
	List<Object[]> buscarTodosProyectos();

	@Query(value = "SELECT p.id_proyecto, p.nombre_corto " + "FROM sisecofi.sscft_proyecto p "
			+ "INNER JOIN sisecofi.sscfr_usuario_asignado ua ON p.id_proyecto = ua.id_proyecto "
			+ "WHERE ua.id_user = :idUser AND ua.estatus = true AND p.id_estatus_proyecto IN (:estatusProyecto) "
			+ "ORDER BY p.nombre_corto ASC", nativeQuery = true)
	List<Object[]> findByIdEstatusProyectoAndUserOrdenado(@Param("idUser") int idUser,
			@Param("estatusProyecto") List<Integer> estatusProyecto);

	@Query(value = "SELECT p.id_proyecto, p.nombre_corto " + "FROM sisecofi.sscft_proyecto p "
			+ "INNER JOIN sisecofi.sscfr_usuario_asignado ua ON p.id_proyecto = ua.id_proyecto "
			+ "WHERE ua.id_user = :idUser AND ua.estatus = true " + "ORDER BY p.nombre_corto ASC", nativeQuery = true)
	List<Object[]> buscarTodosProyectosAndUser(@Param("idUser") int idUser);
}
