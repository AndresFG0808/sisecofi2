package com.sisecofi.reportedocumental.repository.controldoc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoDto;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioAsignadoPk;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface UsuarioProyectoRepository extends JpaRepository<UsuarioProyectoModel, UsuarioAsignadoPk> {

	@Query(nativeQuery = true)
	List<ProyectoDto> buscarProyectosUsuario(Long idUser, List<Integer> estatusProyecto);

	@Query(nativeQuery = true)
	List<ProyectoDto> buscarProyectosUsuarioIds(Long idUser);
	
	@Query("SELECT new com.sisecofi.libreria.comunes.dto.proyecto.ProyectoDto(p.proyectoModel.idProyecto, p.usuario.idUser) " +
		       "FROM UsuarioProyectoModel p " +
		       "WHERE p.estatus = true AND p.usuario.idUser = :idUser")
		List<ProyectoDto> findProyectosConEstatusTrueAndByIdUser(@Param("idUser") Long idUser);

	
	@Query("SELECT new com.sisecofi.libreria.comunes.dto.proyecto.ProyectoDto(p.proyectoModel.idProyecto, p.usuario.idUser) " +
		       "FROM UsuarioProyectoModel p " +
		       "WHERE p.estatus = true AND p.usuario.idUser = :idUser " +
		       "AND p.proyectoModel.idEstatusProyecto IN :estatusProyecto")
		List<ProyectoDto> findProyectosConEstatusTrueByIdUserAndEstatusProyecto(
		        @Param("idUser") Long idUser,
		        @Param("estatusProyecto") List<Integer> estatusProyecto);


}
