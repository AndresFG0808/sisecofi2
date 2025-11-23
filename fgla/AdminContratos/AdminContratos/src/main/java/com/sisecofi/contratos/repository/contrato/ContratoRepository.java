package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContratoRepository
		extends JpaRepository<ContratoModel, Integer>, JpaSpecificationExecutor<ContratoModel> {

	Optional<List<ContratoModel>> findAllByEstatusTrue();

	Optional<ContratoModel> findByIdContrato(Long idContrato);

	Optional<ContratoModel> findByIdContratoAndEstatusTrue(Long idContrato);

	@Query("SELECT c FROM ContratoModel c " + "LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato "
			+ "LEFT JOIN c.conveniosModificatorios conv " + "WHERE c.estatus = :estatus "
			+ "AND (SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato) "
			+ "     FROM ConvenioModificatorioModel conv2 "
			+ "     WHERE conv2.contratoModel.idContrato = c.idContrato) >= :fechaIngresada")
	List<ContratoModel> findContratosConFechaSuperiorYEstado(@Param("fechaIngresada") LocalDateTime fechaIngresada,
			@Param("estatus") Boolean estatus);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato
			    LEFT JOIN c.conveniosModificatorios conv
			    WHERE c.estatus = :estatus
			    AND (
			        SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato)
			        FROM ConvenioModificatorioModel conv2
			        WHERE conv2.contratoModel.idContrato = c.idContrato
			    ) >= :fechaIngresada
			    GROUP BY c.idContrato, c.nombreCorto, c.idEstatusContrato
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findContratosConFechaSuperiorYEstadoDto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus);

	@Query("SELECT c FROM ContratoModel c " + "LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato "
			+ "LEFT JOIN c.conveniosModificatorios conv "
			+ "JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato " + "WHERE c.estatus = :estatus "
			+ "AND p.idUser = :idUser " + "AND p.estatus = true " + "AND p.vigente = true "
			+ "AND (SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato) "
			+ "     FROM ConvenioModificatorioModel conv2 "
			+ "     WHERE conv2.contratoModel.idContrato = c.idContrato) >= :fechaIngresada")
	List<ContratoModel> findContratosConFechaSuperiorYEstadoYUsuario(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato
			    LEFT JOIN c.conveniosModificatorios conv
			    LEFT JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato
			        AND p.idUser = :idUser
			        AND p.estatus = true
			        AND p.vigente = true
			    LEFT JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto
			        AND up.usuario.idUser = :idUser
			        AND up.estatus = true
			    WHERE c.estatus = :estatus
			    AND (
			        p.idUser IS NOT NULL OR up.usuario.idUser IS NOT NULL
			    )
			    AND (
			        SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato)
			        FROM ConvenioModificatorioModel conv2
			        WHERE conv2.contratoModel.idContrato = c.idContrato
			    ) >= :fechaIngresada
			    GROUP BY c.idContrato, c.nombreCorto, c.idEstatusContrato
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findContratosConFechaSuperiorYEstadoYUsuarioDto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("SELECT c FROM ContratoModel c " + "LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato "
			+ "LEFT JOIN c.conveniosModificatorios conv " + "WHERE c.estatus = :estatus "
			+ "AND (SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato) "
			+ "     FROM ConvenioModificatorioModel conv2 "
			+ "     WHERE conv2.contratoModel.idContrato = c.idContrato) < :fechaIngresada")
	List<ContratoModel> findContratosConFechaMenorYEstado(@Param("fechaIngresada") LocalDateTime fechaIngresada,
			@Param("estatus") Boolean estatus);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato
			    LEFT JOIN c.conveniosModificatorios conv
			    WHERE c.estatus = :estatus
			    AND (
			        SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato)
			        FROM ConvenioModificatorioModel conv2
			        WHERE conv2.contratoModel.idContrato = c.idContrato
			    ) < :fechaIngresada
			    GROUP BY c.idContrato, c.nombreCorto, c.idEstatusContrato
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findContratosConFechaMenorYEstadoDto(@Param("fechaIngresada") LocalDateTime fechaIngresada,
			@Param("estatus") Boolean estatus);

	@Query("SELECT c FROM ContratoModel c " + "LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato "
			+ "LEFT JOIN c.conveniosModificatorios conv "
			+ "JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato " + "WHERE c.estatus = :estatus "
			+ "AND p.idUser = :idUser " + "AND p.estatus = true " + "AND p.vigente = true "
			+ "AND (SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato) "
			+ "     FROM ConvenioModificatorioModel conv2 "
			+ "     WHERE conv2.contratoModel.idContrato = c.idContrato) < :fechaIngresada")
	List<ContratoModel> findContratosConFechaMenorYEstadoYUsuario(@Param("fechaIngresada") LocalDateTime fechaIngresada,
			@Param("estatus") Boolean estatus, @Param("idUser") Long idUser);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato
			    LEFT JOIN c.conveniosModificatorios conv
			    LEFT JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato
			        AND p.idUser = :idUser
			        AND p.estatus = true
			        AND p.vigente = true
			    LEFT JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto
			        AND up.usuario.idUser = :idUser
			        AND up.estatus = true
			    WHERE c.estatus = :estatus
			    AND (
			        p.idUser IS NOT NULL OR up.usuario.idUser IS NOT NULL
			    )
			    AND (
			        SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato)
			        FROM ConvenioModificatorioModel conv2
			        WHERE conv2.contratoModel.idContrato = c.idContrato
			    ) < :fechaIngresada
			    GROUP BY c.idContrato, c.nombreCorto, c.idEstatusContrato
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findContratosConFechaMenorYEstadoYUsuarioDto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	List<ContratoModel> findByEstatus(Boolean estatus);

	@Query("""
			    SELECT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    WHERE c.estatus = :estatus
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findByEstatusDto(@Param("estatus") Boolean estatus);

	@Query("SELECT c FROM ContratoModel c " + "JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato "
			+ "WHERE c.estatus = :estatus " + "AND p.idUser = :idUser " + "AND p.estatus = true "
			+ "AND p.vigente = true")
	List<ContratoModel> findByEstatusAndUsuario(@Param("estatus") Boolean estatus, @Param("idUser") Long idUser);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato
			        AND p.idUser = :idUser
			        AND p.estatus = true
			        AND p.vigente = true
			    LEFT JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto
			        AND up.usuario.idUser = :idUser
			        AND up.estatus = true
			    WHERE c.estatus = :estatus
			    AND (
			        p.idUser IS NOT NULL OR up.usuario.idUser IS NOT NULL
			    )
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findByEstatusAndUsuarioDto(@Param("estatus") Boolean estatus, @Param("idUser") Long idUser);

	Optional<ContratoModel> findByNombreCortoAndEstatusTrue(String nombreCorto);

	Optional<ContratoModel> findByNombreCorto(String nombreCorto);

	Boolean existsByNombreCortoAndNombreContratoAndEstatusTrue(String nombreCorto, String nombreContrato);

	Optional<ContratoModel> findByNombreCortoOrNombreContratoAndEstatusTrue(String nombreCorto, String nombreContrato);

	@Query("SELECT c FROM ContratoModel c " + "LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato "
			+ "LEFT JOIN c.conveniosModificatorios conv "
			+ "JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto "
			+ "WHERE c.estatus = :estatus " + "AND up.usuario.idUser = :idUser " + "AND up.estatus = true "
			+ "AND (SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato) "
			+ "     FROM ConvenioModificatorioModel conv2 "
			+ "     WHERE conv2.contratoModel.idContrato = c.idContrato) < :fechaIngresada")
	List<ContratoModel> findContratosConFechaMenorYEstadoYUsuarioProyecto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato
			    LEFT JOIN c.conveniosModificatorios conv
			    LEFT JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato
			        AND p.idUser = :idUser
			        AND p.estatus = true
			        AND p.vigente = true
			    LEFT JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto
			        AND up.usuario.idUser = :idUser
			        AND up.estatus = true
			    WHERE c.estatus = :estatus
			    AND (
			        p.idUser IS NOT NULL OR up.usuario.idUser IS NOT NULL
			    )
			    AND (
			        SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato)
			        FROM ConvenioModificatorioModel conv2
			        WHERE conv2.contratoModel.idContrato = c.idContrato
			    ) < :fechaIngresada
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findContratosConFechaMenorYEstadoYUsuarioProyectoDto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("SELECT c FROM ContratoModel c " + "LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato "
			+ "LEFT JOIN c.conveniosModificatorios conv "
			+ "JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto "
			+ "WHERE c.estatus = :estatus " + "AND up.usuario.idUser = :idUser " + "AND up.estatus = true "
			+ "AND (SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato) "
			+ "     FROM ConvenioModificatorioModel conv2 "
			+ "     WHERE conv2.contratoModel.idContrato = c.idContrato) >= :fechaIngresada")
	List<ContratoModel> findContratosConFechaSuperiorYEstadoYUsuarioProyecto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN VigenciaMontosModel v ON v.idContrato = c.idContrato
			    LEFT JOIN c.conveniosModificatorios conv
			    LEFT JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato
			        AND p.idUser = :idUser
			        AND p.estatus = true
			        AND p.vigente = true
			    LEFT JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto
			        AND up.usuario.idUser = :idUser
			        AND up.estatus = true
			    WHERE c.estatus = :estatus
			    AND (
			        p.idUser IS NOT NULL OR up.usuario.idUser IS NOT NULL
			    )
			    AND (
			        SELECT COALESCE(MAX(conv2.fechaFin), v.fechaFinVigenciaContrato)
			        FROM ConvenioModificatorioModel conv2
			        WHERE conv2.contratoModel.idContrato = c.idContrato
			    ) >= :fechaIngresada
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findContratosConFechaSuperiorYEstadoYUsuarioProyectoDto(
			@Param("fechaIngresada") LocalDateTime fechaIngresada, @Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("SELECT c FROM ContratoModel c "
			+ "JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto "
			+ "WHERE c.estatus = :estatus " + "AND up.usuario.idUser = :idUser " + "AND up.estatus = true")
	List<ContratoModel> findByEstatusAndUsuarioProyecto(@Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query("""
			    SELECT DISTINCT new com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto(
			        c.idContrato,
			        c.nombreCorto,
			        c.idEstatusContrato
			    )
			    FROM ContratoModel c
			    LEFT JOIN ParticipantesAdministracionModel p ON p.idContrato = c.idContrato
			        AND p.idUser = :idUser
			        AND p.estatus = true
			        AND p.vigente = true
			    LEFT JOIN UsuarioProyectoModel up ON up.proyectoModel.idProyecto = c.proyecto.idProyecto
			        AND up.usuario.idUser = :idUser
			        AND up.estatus = true
			    WHERE c.estatus = :estatus
			    AND (
			        p.idUser IS NOT NULL OR up.usuario.idUser IS NOT NULL
			    )
			    ORDER BY c.nombreCorto ASC
			""")
	List<ContratoSimpleDto> findByEstatusAndUsuarioProyectoDto(@Param("estatus") Boolean estatus,
			@Param("idUser") Long idUser);

	@Query(value = """
			SELECT sua.id_user AS idUser,
			       sp.id_proyecto AS idProyecto,
			       sp.nombre_proyecto AS nombreProyecto,
			       sc.nombre_contrato AS nombreContrato
			FROM sisecofi.sscfr_usuario_asignado sua
			INNER JOIN sisecofi.sscft_proyecto sp ON sp.id_proyecto = sua.id_proyecto
			INNER JOIN sisecofi.sscft_contrato sc ON sc.id_proyecto = sp.id_proyecto
			WHERE sua.id_user = :idUser
			  AND sua.estatus = true
			""", nativeQuery = true)
	List<Object[]> findContratosByUserId(@Param("idUser") Long idUser);

	@Query(value = "SELECT c.id_contrato AS idContrato, "
			+ "STRING_AGG(p.nombre_proveedor, ', ' ORDER BY p.nombre_proveedor) AS proveedoresConcatenados "
			+ "FROM sisecofi.sscft_contrato c "
			+ "JOIN sisecofi.sscfr_asociacion_contrato_proveedor acp ON c.id_contrato = acp.id_contrato "
			+ "JOIN sisecofi.sscft_proveedor p ON acp.id_proveedor = p.id_proveedor "
			+ "WHERE c.id_contrato = :idContrato " + "GROUP BY c.id_contrato", nativeQuery = true)
	String findProveedoresConcatenadosByIdContrato(@Param("idContrato") Long idContrato);

	@Query("SELECT new com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto(c.idContrato, c.nombreCorto) "
			+ "FROM ContratoModel c " + "WHERE c.estatus = true")
	List<ContratoNombreDto> findAllContratosAsDto();

	@Query("SELECT new com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto( " + "c.idContrato, "
			+ "c.nombreCorto) " + "FROM ContratoModel c "
			+ "WHERE c.estatus = true AND c.proyecto.idProyecto = :idProyecto")
	List<ContratoNombreDto> findContratosByProyectoId(@Param("idProyecto") Long idProyecto);

	@Query("SELECT c.idContrato, c.nombreContrato, c.nombreCorto, " 
	        + "COALESCE(dg.numeroContrato, ''), "
	        + "c.fechaUltimaModificacion, c.estatus, c.idEstatusContrato, "
	        + "COALESCE(ec.nombre, ''), c.ultimoModificador, c.actaCierreRuta, "
	        + "COALESCE(v.fechaInicioVigenciaContrato, NULL), "
	        + "COALESCE(v.fechaFinVigenciaContrato, NULL), "
	        + "COALESCE(v.montoMaximoSinImpuestos, 0.0), "
	        + "COALESCE(cm.montoMaximoSinImpuestos, 0.0), "
	        + "COALESCE(cm.montoPesos, v.montoPesosSinImpuestos, 0.0), "
	        + "(SELECT COUNT(sc) > 0 FROM ServicioContratoModel sc WHERE sc.contratoModel = c AND sc.ieps = true), "
	        + "(SELECT COALESCE( "
	        + "   CASE "
	        + "     WHEN pa.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = pa.idReferencia) "
	        + "     WHEN pa.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = pa.idReferencia) "
	        + "     WHEN pa.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = pa.idReferencia) "
	        + "     WHEN pa.nivel = 4 THEN (SELECT cea.nombre FROM CatEmpleadoAdministracion cea WHERE cea.idEmpleadoAdministracion = pa.idReferencia) "
	        + "     ELSE '' "
	        + "   END, '' ) "
	        + " FROM ParticipantesAdministracionModel pa "
	        + " WHERE pa.contratoModel = c AND pa.idResponsabilidad = 1 "
	        + "   AND pa.vigente = true AND pa.estatus = true), "
	        + "(SELECT COALESCE(ac.acronimo, '') "
	        + " FROM ParticipantesAdministracionModel pa JOIN pa.catAdmonCentral ac "
	        + " WHERE pa.idParticipantesAdministracionContrato = ("
	        + "     SELECT MAX(pa2.idParticipantesAdministracionContrato) "
	        + "     FROM ParticipantesAdministracionModel pa2 "
	        + "     WHERE pa2.contratoModel = c "
	        + "     AND pa2.idResponsabilidad = 6 "
	        + "     AND pa2.vigente = true "
	        + "     AND pa2.estatus = true"
	        + " )), "
	        + "COALESCE(p.nombreCorto, ''), "
	        + "(SELECT COALESCE(STRING_AGG(pr.nombreProveedor, '; '), '') "
	        + " FROM AsociacionContratoProveedorModel ap "
	        + " JOIN ap.proveedorModel pr "
	        + " WHERE ap.contratoModel = c AND ap.estatus = true), "
	        + "(SELECT COALESCE(tp.nombre, '') "
	        + " FROM DatosGeneralesContratoModel dg JOIN dg.catTipoProcedimiento tp "
	        + " WHERE dg.contratoModel = c), "
	        + "(SELECT MAX(cm2.fechaFin) FROM ConvenioModificatorioModel cm2 WHERE cm2.contratoModel = c) "
	        + "FROM ContratoModel c "
	        + "LEFT JOIN c.proyecto p "
	        + "LEFT JOIN c.catEstatusContrato ec "
	        + "LEFT JOIN c.datosGenerales dg "
	        + "LEFT JOIN c.vigencia v "
	        + "LEFT JOIN c.conveniosModificatorios cm ON cm.idConvenioModificatorio = ("
	        + "    SELECT MAX(cm2.idConvenioModificatorio) FROM ConvenioModificatorioModel cm2 WHERE cm2.contratoModel = c) "
	        + "WHERE (:idContrato IS NULL OR c.idContrato = :idContrato) "
	        + "AND (:idEstatusContrato IS NULL OR c.idEstatusContrato = :idEstatusContrato) "
	        + "AND (:estatus IS NULL OR c.estatus = :estatus) "
	        + "AND (:idAdministracionCentral IS NULL OR EXISTS ("
	        + "     SELECT 1 FROM ParticipantesAdministracionModel pa "
	        + "     WHERE pa.contratoModel = c AND pa.idAdmonCentral = :idAdministracionCentral)) "
	        + "AND (:idProveedor IS NULL OR EXISTS ("
	        + "     SELECT 1 FROM AsociacionContratoProveedorModel acp "
	        + "     WHERE acp.contratoModel = c AND acp.idProveedor = :idProveedor)) "
	        + "AND (:idUsuario IS NULL OR EXISTS ("
	        + "     SELECT 1 FROM ParticipantesAdministracionModel pa "
	        + "     WHERE pa.contratoModel = c AND pa.usuario.idUser = :idUsuario AND pa.estatus = true)) "
	        + "ORDER BY LOWER(c.nombreCorto) ASC")
	Page<Object[]> findAllOptimizedRaw(@Param("idContrato") Long idContrato,
	        @Param("idEstatusContrato") Integer idEstatusContrato,
	        @Param("estatus") Boolean estatus,
	        @Param("idAdministracionCentral") Integer idAdministracionCentral,
	        @Param("idProveedor") Long idProveedor,
	        @Param("idUsuario") Long idUsuario,
	        Pageable pageable);


	@Query("SELECT c.idContrato, c.nombreContrato, c.nombreCorto, "
	        + "COALESCE(dg.numeroContrato, ''), "
	        + "c.fechaUltimaModificacion, c.estatus, c.idEstatusContrato, "
	        + "COALESCE(ec.nombre, ''), c.ultimoModificador, c.actaCierreRuta, "
	        + "COALESCE(v.fechaInicioVigenciaContrato, NULL), "
	        + "COALESCE(v.fechaFinVigenciaContrato, NULL), "
	        + "COALESCE(v.montoMaximoSinImpuestos, 0.0), "
	        + "COALESCE(cm.montoMaximoSinImpuestos, 0.0), "
	        + "COALESCE(cm.montoPesos, v.montoPesosSinImpuestos, 0.0), "
	        + "(SELECT COUNT(sc) > 0 FROM ServicioContratoModel sc WHERE sc.contratoModel = c AND sc.ieps = true), "
	        + "(SELECT COALESCE( "
	        + "  CASE "
	        + "    WHEN pa.nivel = 1 THEN (SELECT cag.administrador FROM CatAdministradorGeneral cag WHERE cag.idAdministradorGeneral = pa.idReferencia) "
	        + "    WHEN pa.nivel = 2 THEN (SELECT cac.administrador FROM CatAdministradorCentral cac WHERE cac.idAdministradorCentral = pa.idReferencia) "
	        + "    WHEN pa.nivel = 3 THEN (SELECT caa.administrador FROM CatAdministradorAdministracion caa WHERE caa.idAdministradorAdministracion = pa.idReferencia) "
	        + "    WHEN pa.nivel = 4 THEN (SELECT cea.nombre FROM CatEmpleadoAdministracion cea WHERE cea.idEmpleadoAdministracion = pa.idReferencia) "
	        + "    ELSE '' "
	        + "  END, '' ) "
	        + " FROM ParticipantesAdministracionModel pa "
	        + " WHERE pa.contratoModel = c AND pa.idResponsabilidad = 1 "
	        + "   AND pa.vigente = true AND pa.estatus = true), "
	        + "(SELECT COALESCE(ac.acronimo, '') FROM ParticipantesAdministracionModel pa JOIN pa.catAdmonCentral ac "
	        + " WHERE pa.contratoModel = c AND pa.idResponsabilidad = 6 "
	        + " AND pa.vigente = true AND pa.estatus = true), "
	        + "COALESCE(p.nombreCorto, ''), "
	        + "(SELECT COALESCE(STRING_AGG(pr.nombreProveedor, '; '), '') FROM AsociacionContratoProveedorModel ap "
	        + " JOIN ap.proveedorModel pr WHERE ap.contratoModel = c AND ap.estatus = true), "
	        + "(SELECT COALESCE(tp.nombre, '') FROM DatosGeneralesContratoModel dg JOIN dg.catTipoProcedimiento tp "
	        + " WHERE dg.contratoModel = c), "
	        + "(SELECT MAX(cm2.fechaFin) FROM ConvenioModificatorioModel cm2 WHERE cm2.contratoModel = c) "
	        + "FROM ContratoModel c "
	        + "LEFT JOIN c.proyecto p "
	        + "LEFT JOIN c.catEstatusContrato ec "
	        + "LEFT JOIN c.datosGenerales dg "
	        + "LEFT JOIN c.vigencia v "
	        + "LEFT JOIN c.conveniosModificatorios cm ON cm.idConvenioModificatorio = "
	        + "    (SELECT MAX(cm2.idConvenioModificatorio) FROM ConvenioModificatorioModel cm2 WHERE cm2.contratoModel = c) "
	        + "WHERE (:idContrato IS NULL OR c.idContrato = :idContrato) "
	        + "AND (:idEstatusContrato IS NULL OR c.idEstatusContrato = :idEstatusContrato) "
	        + "AND (:estatus IS NULL OR c.estatus = :estatus) "
	        + "AND (:idAdministracionCentral IS NULL OR EXISTS "
	        + "     (SELECT 1 FROM ParticipantesAdministracionModel pa "
	        + "      WHERE pa.contratoModel = c AND pa.idAdmonCentral = :idAdministracionCentral)) "
	        + "AND (:idProveedor IS NULL OR EXISTS "
	        + "     (SELECT 1 FROM AsociacionContratoProveedorModel acp "
	        + "      WHERE acp.contratoModel = c AND acp.idProveedor = :idProveedor)) "
	        + "AND (:idUsuario IS NULL OR EXISTS "
	        + "     (SELECT 1 FROM ParticipantesAdministracionModel pa "
	        + "      WHERE pa.contratoModel = c AND pa.usuario.idUser = :idUsuario AND pa.estatus = true)) "
	        + "ORDER BY LOWER(c.nombreCorto) ASC")
	List<Object[]> findAllOptimizedRaw(@Param("idContrato") Long idContrato,
	        @Param("idEstatusContrato") Integer idEstatusContrato,
	        @Param("estatus") Boolean estatus,
	        @Param("idAdministracionCentral") Integer idAdministracionCentral,
	        @Param("idProveedor") Long idProveedor,
	        @Param("idUsuario") Long idUsuario);


	@Modifying
	@Query("UPDATE ContratoModel c SET c.fechaUltimaModificacion = :fecha, c.ultimoModificador = :usuario WHERE c.idContrato = :idContrato")
	void updateFechaUltimaModificacion(@Param("idContrato") Long idContrato, @Param("fecha") LocalDateTime fecha,
			@Param("usuario") String usuario);

	@Query("SELECT new com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun( " + "c.idContrato, "
			+ "COALESCE(v.fechaInicioVigenciaContrato, NULL), "
			+ "COALESCE((SELECT MAX(cm2.fechaFin) FROM ConvenioModificatorioModel cm2 WHERE cm2.contratoModel = c), v.fechaFinVigenciaContrato), "
			+ "v.id_iva, " + "v.tipoCambioMaximo, "
			+ "CASE WHEN v.idTipoMoneda = 1 THEN 'MXN' ELSE COALESCE(v.catTipoMoneda.nombre, '') END, "
			+ "CASE WHEN c.idEstatusContrato = 2 THEN true ELSE false END, " + "c.nombreContrato, " + "c.nombreCorto, "
			+ "COALESCE(dg.numeroContrato, '') ) " + "FROM ContratoModel c " + "LEFT JOIN c.vigencia v "
			+ "LEFT JOIN c.datosGenerales dg " + "WHERE c.idContrato = :idContrato")
	Optional<ContratoDtoLigeroComun> findContratoById(@Param("idContrato") Long idContrato);

}
