package com.sisecofi.libreria.comunes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.dto.reportedinamico.EmpleadoDto;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/*
	 * metodo unicamente para cargar un usaurio local en modo DEV no usar para PROD
	 */
	@Query("select u from Usuario u where u.userName = :userName ")
	Optional<Usuario> findByUserName(@Param("userName") String userName);

	/*
	 * metodo unicamente para cargar un usaurio local en modo DEV no usar para PROD
	 */
	@Query(value = "select id_rol from usuario u  inner join usuario_rol ur on u.id_user  = ur.id_usuario where u.id_empleado  = :idEmpleado", nativeQuery = true)
	List<Object[]> obtenerRol1(@Param("idEmpleado") long idEmpleado);

	/*
	 * metodo unicamente para cargar un usaurio local en modo DEV no usar para PROD
	 */
	@Query(value = "select id_user from usuario u where u.id_empleado  = :idEmpleado", nativeQuery = true)
	Long buscarId1(@Param("idEmpleado") long idEmpleado);

	List<Usuario> findByNombreOrRfcCortoAndEstatus(String nombre, String rfcCorto, boolean estatus);
	
	@Query("SELECT u FROM Usuario u WHERE "
	        + "u.estatus = :estatus "  // Siempre filtrar por estatus primero
	        + "AND ("
	        + "(:nombre IS NOT NULL AND :nombre <> '' AND LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) "
	        + "OR (:rfcCorto IS NOT NULL AND :rfcCorto <> '' AND u.rfcCorto = :rfcCorto)"
	        + ")")
	List<Usuario> findByNombreOrRfcCortoAndEstatusMinuscula(
	    @Param("nombre") String nombre, 
	    @Param("rfcCorto") String rfcCorto, 
	    @Param("estatus") boolean estatus
	);

	@Query("SELECT u FROM Usuario u WHERE "
	          + "u.estatus = :estatus "
	          + "AND ("
	          + "(:nombres IS NULL OR :nombres = '' OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))) "
	          + "AND (:apellidoPaterno IS NULL OR :apellidoPaterno = '' OR LOWER(u.apellidoPaterno) LIKE LOWER(CONCAT('%', :apellidoPaterno, '%'))) "
	          + "AND (:apellidoMaterno IS NULL OR :apellidoMaterno = '' OR LOWER(u.apellidoMaterno) LIKE LOWER(CONCAT('%', :apellidoMaterno, '%'))) "
	          + "AND (:rfcCorto IS NULL OR :rfcCorto = '' OR LOWER(u.rfcCorto) LIKE LOWER(CONCAT('%', :rfcCorto, '%'))) "
	          + ")")
	  List<Usuario> findByNombreOrRfcCortoAndEstatusAcumulativo(
	      @Param("nombres") String nombres, 
	      @Param("apellidoPaterno") String apellidoPaterno, 
	      @Param("apellidoMaterno") String apellidoMaterno, 
	      @Param("rfcCorto") String rfcCorto, 
	      @Param("estatus") boolean estatus
	  );




	List<Usuario> findByEstatus(boolean estatus);
	
	List<Usuario> findByEstatusOrderByNombreAsc(boolean estatus);
	
	@Query("SELECT u FROM Usuario u " +
		       "WHERE u.estatus = true " +
			"ORDER BY LOWER(u.nombre) ASC")
		List<Usuario> findUsuariosOrdenados();
	
	
	@Query("SELECT u FROM Usuario u " +
		       "WHERE u.estatus = true " +
		       "AND u NOT IN (SELECT up.usuario FROM UsuarioProyectoModel up " +
		       "              WHERE up.proyectoModel.idProyecto = :idProyecto " +
		       "              AND up.estatus = true) " +
			"ORDER BY LOWER(u.nombre) ASC")
		List<Usuario> findUsuariosNoAsignados(@Param("idProyecto") Long idProyecto);

	
	@Query("SELECT u FROM Usuario u " +
		       "WHERE u IN (SELECT up.usuario FROM UsuarioProyectoModel up " +
		       "            WHERE up.proyectoModel.idProyecto = :idProyecto " +
		       "            AND up.estatus = true) " +
		       "ORDER BY LOWER(u.nombre) ASC")
		List<Usuario> findUsuariosAsignados(@Param("idProyecto") Long idProyecto);


	Optional<Usuario> findByIdUser(Long idUser);

	/*
	 * metodo unicamente para cargar un usaurio local en modo DEV no usar para PROD
	 */
	@Query("select u from Usuario u where u.rfcCorto = :rfcCorto ")
	Optional<Usuario> findByRfcLargo(@Param("rfcCorto") String rfcCorto);

	@Query(nativeQuery = true)
	List<EmpleadoDto> buscarUsuariosActivos(boolean estatus);

	@Query(value = "select distinct u.* from sscft_usuario u inner join sscft_Papelera p on p.id_user  = u.id_user order by u.id_user asc", nativeQuery = true)
	List<Usuario> buscarUsuarioPapeleraReciclaje();
	
	@Query("select u from Usuario u where u.userName = :userName or u.rfcLargo= :userName")
	Optional<Usuario> findByUserNameOrRfcLargo(@Param("userName") String userName);

}
