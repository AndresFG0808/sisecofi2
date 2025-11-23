package com.sisecofi.proyectos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface UsuarioProyectoRepository
		extends JpaRepository<UsuarioProyectoModel, Long>, JpaSpecificationExecutor<UsuarioProyectoModel> {

	List<UsuarioProyectoModel> findByProyectoModelIdProyectoAndEstatus(Long idProyecto, boolean estatus);

	List<UsuarioProyectoModel> findByUsuarioIdUserAndEstatus(Long idUser, boolean estatus);

	@Query("update UsuarioProyectoModel set estatus=:estatus where usuario.idUser = :idUser")
	@Modifying
	void actualizarProyectos(@Param("idUser") Long idUser, @Param("estatus") Boolean estatus);

	@Query("update UsuarioProyectoModel set estatus=:estatus where proyectoModel.idProyecto = :idProyecto")
	@Modifying
	void actualizarProyectosUsuario(@Param("idProyecto") Long idProyecto, @Param("estatus") boolean estatus);

	List<UsuarioProyectoModel> findByProyectoModelIdProyectoAndProyectoModelNombreCortoAndProyectoModelIdEstatusProyecto(
			Long idProyecto, String nombreCorto, Integer idEstatusProyecto);
	
	@Query("SELECT u.proyectoModel.nombreCorto FROM UsuarioProyectoModel u " +
		       "WHERE u.usuario = :usuario AND u.estatus = true " +
		       "AND u.proyectoModel.estatus = true " +
		       "AND u.proyectoModel.idEstatusProyecto = :idEstatusProyecto")
		List<String> findNombreCortoByUsuarioAndEstatusTrueAndIdEstatusProyecto(
		        @Param("usuario") Usuario usuario,
		        @Param("idEstatusProyecto") Integer idEstatusProyecto);
	
	@Query("SELECT new com.sisecofi.proyectos.dto.ProyectoNombreDto( " +
		       "u.proyectoModel.idProyecto, u.proyectoModel.nombreCorto, u.proyectoModel.idEstatusProyecto) " +
		       "FROM UsuarioProyectoModel u " +
		       "WHERE u.usuario = :usuario AND u.estatus = true " +
		       "AND u.proyectoModel.estatus = true " +
		       "AND u.proyectoModel.idEstatusProyecto = :idEstatusProyecto")
		List<ProyectoNombreDto> findProyectosDtoByUsuarioAndEstatusTrueAndIdEstatusProyecto(
		        @Param("usuario") Usuario usuario,
		        @Param("idEstatusProyecto") Integer idEstatusProyecto);


}
