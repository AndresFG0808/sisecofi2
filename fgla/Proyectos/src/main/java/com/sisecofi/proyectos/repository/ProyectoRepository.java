package com.sisecofi.proyectos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProyectoRepository
		extends JpaRepository<ProyectoModel, Long>, JpaSpecificationExecutor<ProyectoModel> {

	List<ProyectoModel> findByIdEstatusProyecto(Long idEstatusProyecto);

	List<ProyectoModel> findAllByEstatusTrue();

	List<ProyectoModel> findByEstatusTrue();

	@Query("SELECT new com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto(p.nombreProyecto, p.nombreCorto, p.idProyecto) FROM ProyectoModel p WHERE p.estatus = true")
	List<ProyectoSimpleDto> findProyectosConEstatusTrue();

	@Query("SELECT new com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto(p.nombreProyecto, p.nombreCorto, p.idProyecto) FROM ProyectoModel p WHERE p.idEstatusProyecto = :idEstatusProyecto AND p.estatus = true")
	List<ProyectoSimpleDto> findProyectosByIdEstatusProyecto(@Param("idEstatusProyecto") Integer idEstatusProyecto);

	List<ProyectoModel> findAllByIdEstatusProyectoAndEstatusTrue(Integer idEstatusProyecto);

	@Query(value = "SELECT p.* " + "FROM sisecofi.sscft_proyecto p "
			+ "INNER JOIN sisecofi.sscfr_usuario_asignado ua ON p.id_proyecto = ua.id_proyecto "
			+ "WHERE p.id_estatus_proyecto = :idEstatusProyecto " + "AND ua.id_user = :idUser "
			+ "AND ua.estatus = true " + "ORDER BY p.nombre_corto ASC", nativeQuery = true)
	List<ProyectoModel> findByIdEstatusProyectoAndUserAndEstatusTrue(
			@Param("idEstatusProyecto") Integer idEstatusProyecto, @Param("idUser") Long idUser);

	@Query(value = "SELECT p.* " + "FROM sisecofi.sscft_proyecto p "
			+ "INNER JOIN sisecofi.sscfr_usuario_asignado ua ON p.id_proyecto = ua.id_proyecto "
			+ "WHERE ua.id_user = :idUser " + "AND ua.estatus = true", nativeQuery = true)
	List<ProyectoModel> findByUserAndEstatusTrue(@Param("idUser") Long idUser);

	List<ProyectoModel> findByNombreEmpleado(String empleado);

	Optional<ProyectoModel> findByIdAgp(String idAgp);

	ProyectoModel findTopByOrderByIdProyectoDesc();

	Optional<ProyectoModel> findByIdProyectoAndEstatus(Long id, boolean estatus);

	Optional<ProyectoModel> findByNombreCortoAndIdEstatusProyecto(String nombreCorto, Integer idEstatusProyecto);

	List<ProyectoModel> findByIdProyectoNotIn(List<Long> proyectosAsignados);

	List<ProyectoModel> findByIdProyectoIn(List<Long> proyectosAsignados);

	Collection<ProyectoModel> findByCatEstatusProyectoIdEstatusProyecto(Integer primaryKey);

	@Query("SELECT new com.sisecofi.proyectos.dto.ProyectoNombreDto(p.idProyecto, p.nombreCorto, p.idEstatusProyecto) "
			+ "FROM ProyectoModel p " + "WHERE p.catEstatusProyecto.idEstatusProyecto = :idEstatusProyecto "
			+ "ORDER BY LOWER(p.nombreCorto) ASC")
	List<ProyectoNombreDto> findProyectosByEstatusDto(@Param("idEstatusProyecto") Integer idEstatusProyecto);

	@Query("SELECT p.nombreCorto FROM ProyectoModel p")
	List<String> findAllNombreCorto();

	@Query("SELECT new com.sisecofi.proyectos.dto.ProyectoNombreDto( "
			+ "p.idProyecto, p.nombreCorto, p.idEstatusProyecto) " + "FROM ProyectoModel p " + "WHERE p.estatus = true")
	List<ProyectoNombreDto> findAllProyectosActivosDto();

	@Query("SELECT p.nombreCorto FROM ProyectoModel p WHERE p.estatus = true AND p.idEstatusProyecto = :idEstatusProyecto")
	List<String> findAllNombreCortoActivosEstatus(@Param("idEstatusProyecto") Integer idEstatusProyecto);

	Optional<ProyectoModel> findByNombreCorto(String nombreCorto);

	List<ProyectoModel> findByEstatus(boolean estatus);

	@Query("""
			SELECT p.idProyecto, p.nombreProyecto, p.nombreCorto,
			       p.idAgp, p.idEstatusProyecto, COALESCE(ce.nombre, ''),
			       (SELECT COALESCE(STRING_AGG(fa.catAdmonCentral.administracion, ', '), ' ')
			        FROM FichaAdmonCentral fa
			        WHERE fa.fichaTecnicaModel = p.fichaTecnicaModel AND fa.estatus = true),
			       (SELECT COALESCE(
			           CASE
			             WHEN h.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = h.idReferencia)
			             WHEN h.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = h.idReferencia)
			             WHEN h.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = h.idReferencia)
			             WHEN h.nivel = 4 THEN (SELECT cea.nombre         FROM CatEmpleadoAdministracion     cea WHERE cea.idEmpleadoAdministracion     = h.idReferencia)
			             ELSE ''
			           END
			         , ' ')
			        FROM HistoricoModel h
			        WHERE h.fichaTecnicaModel = p.fichaTecnicaModel
			          AND h.estatus = true
			          AND h.estatusHistorico= true
			          AND h.id = (
			              SELECT MAX(h2.id)
			              FROM HistoricoModel h2
			              WHERE h2.fichaTecnicaModel = p.fichaTecnicaModel
			                AND h2.estatus = true
			          )
			       ),
			       (SELECT COALESCE(f.catAreaPlaneacion.administracion, '')
			        FROM FichaTecnicaModel f WHERE f = p.fichaTecnicaModel),
			       COALESCE(p.fichaTecnicaModel.fechaInicio, CURRENT_DATE),
			       COALESCE(p.fichaTecnicaModel.fechaTermino, CURRENT_DATE),
			       COALESCE(CAST(p.fichaTecnicaModel.montoSolicitado AS double), 0.0),
			       (CASE WHEN EXISTS (SELECT 1 FROM PlanTrabajoModel pt WHERE pt.proyectoModel = p) THEN TRUE ELSE FALSE END)
			FROM ProyectoModel p
			LEFT JOIN p.catEstatusProyecto ce
			LEFT JOIN p.fichaTecnicaModel ft
			WHERE (:idEstatusProyecto IS NULL OR p.idEstatusProyecto = :idEstatusProyecto)
			  AND (:nombreCorto IS NULL OR LOWER(p.nombreCorto) LIKE LOWER(CONCAT('%', :nombreCorto, '%')))
			  AND (:areaResponsable IS NULL OR ft.idAreaPlaneacion = :areaResponsable)
			  AND (:liderProyecto IS NULL OR EXISTS (
			      SELECT 1
			      FROM HistoricoModel h
			      WHERE h.fichaTecnicaModel = ft
			        AND h.estatus = true
			        AND h.id = (
			            SELECT MAX(h2.id)
			            FROM HistoricoModel h2
			            WHERE h2.fichaTecnicaModel = ft
			              AND h2.estatus = true
			        )
			        AND (
			          CASE
			            WHEN h.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = h.idReferencia)
			            WHEN h.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = h.idReferencia)
			            WHEN h.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = h.idReferencia)
			            WHEN h.nivel = 4 THEN (SELECT cea.nombre         FROM CatEmpleadoAdministracion     cea WHERE cea.idEmpleadoAdministracion     = h.idReferencia)
			            ELSE ''
			          END
			        ) = :liderProyecto
			  ))
			  AND (:areaSolicitante IS NULL OR EXISTS (
			       SELECT 1 FROM FichaAdmonCentral fa
			       WHERE fa.fichaTecnicaModel = ft AND fa.idAdmonCentral = :areaSolicitante AND fa.estatus = true))
			  AND (:idUsuario IS NULL OR EXISTS (
			       SELECT 1 FROM UsuarioProyectoModel up
			       WHERE up.asignadoPk.idProyecto = p.idProyecto AND up.asignadoPk.idUser = :idUsuario AND up.estatus = true))
			ORDER BY LOWER(p.nombreCorto) ASC
			""")
	Page<Object[]> findAllOptimizedRaw(@Param("idEstatusProyecto") Integer idEstatusProyecto,
			@Param("nombreCorto") String nombreCorto, @Param("areaResponsable") Integer areaResponsable,
			@Param("liderProyecto") String liderProyecto, @Param("areaSolicitante") Integer areaSolicitante,
			@Param("idUsuario") Long idUsuario, Pageable pageable);

	@Query("""
			SELECT p.idProyecto, p.nombreProyecto, p.nombreCorto,
			       p.idAgp, p.idEstatusProyecto, COALESCE(ce.nombre, ''),
			       (SELECT COALESCE(STRING_AGG(fa.catAdmonCentral.administracion, ', '), ' ')
			        FROM FichaAdmonCentral fa
			        WHERE fa.fichaTecnicaModel = p.fichaTecnicaModel AND fa.estatus = true),
			       (SELECT COALESCE(
			           CASE
			             WHEN h.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = h.idReferencia)
			             WHEN h.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = h.idReferencia)
			             WHEN h.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = h.idReferencia)
			             WHEN h.nivel = 4 THEN (SELECT cea.nombre         FROM CatEmpleadoAdministracion     cea WHERE cea.idEmpleadoAdministracion     = h.idReferencia)
			             ELSE ''
			           END
			         , ' ')
			        FROM HistoricoModel h
			        WHERE h.fichaTecnicaModel = p.fichaTecnicaModel
			          AND h.estatus = true
			          AND h.estatusHistorico= true
			          AND h.id = (
			              SELECT MAX(h2.id)
			              FROM HistoricoModel h2
			              WHERE h2.fichaTecnicaModel = p.fichaTecnicaModel
			                AND h2.estatus = true
			          )
			       ),
			       (SELECT COALESCE(f.catAreaPlaneacion.administracion, '')
			        FROM FichaTecnicaModel f WHERE f = p.fichaTecnicaModel),
			       COALESCE(p.fichaTecnicaModel.fechaInicio, CURRENT_DATE),
			       COALESCE(p.fichaTecnicaModel.fechaTermino, CURRENT_DATE),
			       COALESCE(CAST(p.fichaTecnicaModel.montoSolicitado AS double), 0.0),
			       (CASE WHEN EXISTS (SELECT 1 FROM PlanTrabajoModel pt WHERE pt.proyectoModel = p) THEN TRUE ELSE FALSE END)
			FROM ProyectoModel p
			LEFT JOIN p.catEstatusProyecto ce
			LEFT JOIN p.fichaTecnicaModel ft
			WHERE p.idProyecto = :idProyecto
			ORDER BY LOWER(p.nombreCorto) ASC
			""")
	Page<Object[]> findByIdProyectoOp(@Param("idProyecto") Long idProyecto, Pageable pageable);

	@Query("""
			SELECT p.idProyecto, p.nombreProyecto, p.nombreCorto,
			       p.idAgp, p.idEstatusProyecto, COALESCE(ce.nombre, ''),
			       (SELECT COALESCE(STRING_AGG(fa.catAdmonCentral.administracion, ', '), ' ')
			        FROM FichaAdmonCentral fa
			        WHERE fa.fichaTecnicaModel = p.fichaTecnicaModel),
			       (SELECT COALESCE(
			           CASE
			             WHEN h.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = h.idReferencia)
			             WHEN h.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = h.idReferencia)
			             WHEN h.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = h.idReferencia)
			             WHEN h.nivel = 4 THEN (SELECT cea.nombre         FROM CatEmpleadoAdministracion     cea WHERE cea.idEmpleadoAdministracion     = h.idReferencia)
			             ELSE ''
			           END
			         , ' ')
			        FROM HistoricoModel h
			        WHERE h.fichaTecnicaModel = p.fichaTecnicaModel
			          AND h.estatus = true
			          AND h.id = (
			              SELECT MAX(h2.id)
			              FROM HistoricoModel h2
			              WHERE h2.fichaTecnicaModel = p.fichaTecnicaModel
			                AND h2.estatus = true
			          )
			       ),
			       (SELECT COALESCE(f.catAreaPlaneacion.administracion, '')
			        FROM FichaTecnicaModel f WHERE f = p.fichaTecnicaModel),
			       COALESCE(p.fichaTecnicaModel.fechaInicio, CURRENT_DATE),
			       COALESCE(p.fichaTecnicaModel.fechaTermino, CURRENT_DATE),
			       COALESCE(CAST(p.fichaTecnicaModel.montoSolicitado AS double), 0.0),
			       (CASE WHEN EXISTS (SELECT 1 FROM PlanTrabajoModel pt WHERE pt.proyectoModel = p) THEN TRUE ELSE FALSE END)
			FROM ProyectoModel p
			LEFT JOIN p.catEstatusProyecto ce
			LEFT JOIN p.fichaTecnicaModel ft
			WHERE (:idEstatusProyecto IS NULL OR p.idEstatusProyecto = :idEstatusProyecto)
			  AND (:nombreCorto IS NULL OR LOWER(p.nombreCorto) LIKE LOWER(CONCAT('%', :nombreCorto, '%')))
			  AND (:idProyecto IS NULL OR p.idProyecto = :idProyecto)
			  AND (:areaResponsable IS NULL OR ft.idAreaPlaneacion = :areaResponsable)
			  AND (:liderProyecto IS NULL OR EXISTS (
			       SELECT 1
			       FROM HistoricoModel h
			       WHERE h.fichaTecnicaModel = ft
			         AND h.estatus = true
			         AND h.estatusHistorico= true
			         AND h.id = (
			             SELECT MAX(h2.id)
			             FROM HistoricoModel h2
			             WHERE h2.fichaTecnicaModel = ft
			               AND h2.estatus = true
			         )
			         AND (
			           CASE
			             WHEN h.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = h.idReferencia)
			             WHEN h.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = h.idReferencia)
			             WHEN h.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = h.idReferencia)
			             WHEN h.nivel = 4 THEN (SELECT cea.nombre         FROM CatEmpleadoAdministracion     cea WHERE cea.idEmpleadoAdministracion     = h.idReferencia)
			             ELSE ''
			           END
			         ) = :liderProyecto
			  ))
			  AND (:areaSolicitante IS NULL OR EXISTS (
			       SELECT 1 FROM FichaAdmonCentral fa
			       WHERE fa.fichaTecnicaModel = ft AND fa.idAdmonCentral = :areaSolicitante AND fa.estatus = true))
			  AND (:idUsuario IS NULL OR EXISTS (
			       SELECT 1 FROM UsuarioProyectoModel up
			       WHERE up.asignadoPk.idProyecto = p.idProyecto AND up.asignadoPk.idUser = :idUsuario AND up.estatus = true))
			ORDER BY LOWER(p.nombreCorto) ASC
			""")
	List<Object[]> findAllOptimizedRaw(@Param("idEstatusProyecto") Integer idEstatusProyecto,
			@Param("nombreCorto") String nombreCorto, @Param("idProyecto") Long idProyecto,
			@Param("areaResponsable") Integer areaResponsable, @Param("liderProyecto") String liderProyecto,
			@Param("areaSolicitante") Integer areaSolicitante, @Param("idUsuario") Long idUsuario);

}
