package com.sisecofi.admindevengados.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sisecofi.admindevengados.model.DictaminadoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;

public interface DictaminadoRepository extends JpaRepository<DictaminadoModel, Long> {

	@Query("SELECT d FROM DictaminadoModel d WHERE d.idDictamen = :idDictamen AND d.dictamen.estatus = true")
	List<DictaminadoModel> findByIdDictamenAndDictamenEstatusTrue(@Param("idDictamen") Long idDictamen);

	@Query("SELECT d FROM DictaminadoModel d WHERE d.idDictamen = :idDictamen AND d.dictamen.estatus = true AND d.dictamen.catEstatusDictamen.nombre <> 'Cancelado'")
	List<DictaminadoModel> findByDictamenIdDictamenAndDictamenEstatusTrue(@Param("idDictamen") String idDictamen);

	@Query("SELECT d FROM DictaminadoModel d WHERE d.dictamen.estatus = true AND d.idContrato = :idContrato AND d.idServicioContrato = :idServicioContrato AND d.dictamen.catEstatusDictamen.nombre <> 'Cancelado'")
	List<DictaminadoModel> findByDictamenEstatusTrueAndIdContratoAndIdServicioContrato(
			@Param("idContrato") Long idContrato, @Param("idServicioContrato") Long idServicioContrato);

	@Query("SELECT dm FROM DictaminadoModel dm WHERE dm.dictamen.estatus = true AND dm.idContrato = :idContrato AND dm.idServicioContrato = :idServicioContrato AND dm.seleccionado = true AND dm.idDictamen <> :idDictamen AND dm.dictamen.catEstatusDictamen.nombre <> 'Cancelado'")
	List<DictaminadoModel> findByContratoAndServicioExcludingDictamen(@Param("idContrato") Long idContrato,
			@Param("idServicioContrato") Long idServicioContrato, @Param("idDictamen") String idDictamen);

	@Query("SELECT DISTINCT dm FROM DictaminadoModel dm " + "WHERE dm.dictamen.estatus = true "
			+ "AND dm.idContrato = :idContrato " + "AND dm.seleccionado = true " + "AND dm.idDictamen <> :idDictamen "
			+ "AND dm.dictamen.catEstatusDictamen.nombre NOT IN ('Cancelado', 'Inicial')")
	List<DictaminadoModel> findByContratoExcludingDictamen(@Param("idContrato") Long idContrato,
			@Param("idDictamen") Long idDictamen);

	List<DictaminadoModel> findByDictamenInAndIdServicioContrato(List<Dictamen> dictamenes, Long idServicioContrato);

	List<DictaminadoModel> findByDictamenInAndIdServicioContratoIn(List<Dictamen> dictamenes,
			Set<Long> idServicioContratos);

	List<DictaminadoModel> findByDictamenInAndIdServicioContratoInAndServicioContratoModelGrupoServiciosModelCatTipoConsumoNombre(
			List<Dictamen> dictamenes, Set<Long> idServicioContratos, String nombre);

	List<DictaminadoModel> findByIdContratoAndSeleccionadoTrue(Long idContrato);

	List<DictaminadoModel> findByIdDictamenAndSeleccionadoTrue(Long idDictamen);

	List<DictaminadoModel> findByIdDictamenAndSeleccionadoTrueOrderByIdDictaminadoAsc(Long idDictamen);

	@Query("SELECT dm FROM DictaminadoModel dm " + "WHERE dm.idContrato = :idContrato "
			+ "AND dm.idServicioContrato = :idServicioContrato " + "AND dm.dictamen.estatus = true "
			+ "AND dm.seleccionado = true " + "AND dm.dictamen.catEstatusDictamen.nombre <> 'Cancelado'")
	List<DictaminadoModel> findByContratoAndServicio(@Param("idContrato") Long idContrato,
			@Param("idServicioContrato") Long idServicioContrato);

	@Query("SELECT dm FROM DictaminadoModel dm " + "WHERE dm.idContrato = :idContrato "
			+ "AND dm.idServicioContrato = :idServicioContrato " + "AND dm.idDictamen = :idDictamen "
			+ "AND dm.dictamen.estatus = true " + "AND dm.dictamen.catEstatusDictamen.nombre <> 'Cancelado'")
	DictaminadoModel findByIdDictamen(@Param("idContrato") Long idContrato,
			@Param("idServicioContrato") Long idServicioContrato, @Param("idDictamen") String idDictamen);

	@Query(value = """
			SELECT SUM(t.monto_total) FROM (

			    SELECT SUM(sd.monto_dictaminado * :cantidad) AS monto_total
			    FROM (
			        SELECT DISTINCT ON (gsc.id_grupo_servicio) sd.*
			        FROM sisecofi.sscft_servicios_dictaminados sd
			        INNER JOIN sisecofi.sscft_servicio_contrato sc
			            ON sd.id_servicio_contrato = sc.id_servicio_contrato
			        INNER JOIN sisecofi.sscft_grupo_servicio_contrato gsc
			            ON sc.id_grupo_servicio = gsc.id_grupo_servicio
			        WHERE gsc.id_tipo_consumo = 2
			          AND sc.id_contrato = :idContrato
			          AND sd.id_dictamen = :idDictamen
			          AND (
			              (:idConvenio IS NULL AND sc.ieps = true)
			              OR
			              (:idConvenio IS NOT NULL AND EXISTS (
			                  SELECT 1
			                  FROM sisecofi.sscft_servicio_convenio svc
			                  WHERE svc.id_servicio_contrato = sc.id_servicio_contrato
			                    AND svc.id_convenio_modificatorio = :idConvenio
			                    AND svc.ieps = true
			              ))
			          )
			        ORDER BY gsc.id_grupo_servicio, sd.id_dictaminado
			    ) AS sd

			    UNION ALL

			    SELECT SUM(sd.monto_dictaminado * :cantidad) AS monto_total
			    FROM sisecofi.sscft_servicios_dictaminados sd
			    INNER JOIN sisecofi.sscft_servicio_contrato sc
			        ON sd.id_servicio_contrato = sc.id_servicio_contrato
			    INNER JOIN sisecofi.sscft_grupo_servicio_contrato gsc
			        ON sc.id_grupo_servicio = gsc.id_grupo_servicio
			    WHERE gsc.id_tipo_consumo = 1
			      AND sc.id_contrato = :idContrato
			      AND sd.id_dictamen = :idDictamen
			      AND (
			          (:idConvenio IS NULL AND sc.ieps = true)
			          OR
			          (:idConvenio IS NOT NULL AND EXISTS (
			              SELECT 1
			              FROM sisecofi.sscft_servicio_convenio svc
			              WHERE svc.id_servicio_contrato = sc.id_servicio_contrato
			                AND svc.id_convenio_modificatorio = :idConvenio
			                AND svc.ieps = true
			          ))
			      )

			) AS t
			""", nativeQuery = true)
	BigDecimal calcularMontoIeps(@Param("cantidad") BigDecimal cantidad, @Param("idContrato") Long idContrato,
			@Param("idDictamen") Long idDictamen, @Param("idConvenio") Long idConvenio);

}
