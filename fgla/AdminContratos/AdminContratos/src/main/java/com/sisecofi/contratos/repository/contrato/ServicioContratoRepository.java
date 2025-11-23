package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ServicioContratoRepository
		extends JpaRepository<ServicioContratoModel, Integer>, JpaSpecificationExecutor<ServicioContratoModel> {

	List<ServicioContratoModel> findByIdContrato(Long idContrato);

	List<ServicioContratoModel> findByIdContratoAndEstatusTrue(Long idContrato);
	
	List<ServicioContratoModel> findByIdContratoAndEstatusTrueOrderByOrden(Long idContrato);

	List<ServicioContratoModel> findByIdContratoAndEstatusTrueOrderByIdServicioContrato(Long idContrato);

	@Query("SELECT s FROM ServicioContratoModel s WHERE s.idContrato = :idContrato AND s.estatus=true ORDER BY COALESCE(s.orden, s.idServicioContrato)")
	List<ServicioContratoModel> obtenerServiciosOrdenados(@Param("idContrato") Long idContrato);

	List<ServicioContratoModel> findByIdGrupoServicioAndEstatusTrueAndIdContrato(Long idGrupo, Long idContrato);

	Optional<ServicioContratoModel> findByIdServicioContrato(Long idServicioContrato);

	@Query("SELECT DISTINCT s.idGrupoServicio FROM ServicioContratoModel s WHERE s.idGrupoServicio IN :idsGrupoServicio AND s.estatus = true")
	List<Long> findIdGrupoServicioByIds(@Param("idsGrupoServicio") List<Long> idsGrupoServicio);

	@Query("SELECT COALESCE(MIN(s.idServicioContrato), 0) FROM ServicioContratoModel s WHERE s.idContrato = :idContrato AND s.estatus = true")
	Long obtenerPrimerIdServicioContrato(@Param("idContrato") Long idContrato);

	@Query(value = """
			SELECT *
			FROM sisecofi.sscft_servicio_contrato s
			WHERE s.id_contrato = :idContrato AND s.estatus = true
			ORDER BY
			  COALESCE(s.orden, s.id_servicio_contrato -
			    (SELECT MIN(s2.id_servicio_contrato) - 1
			     FROM sisecofi.sscft_servicio_contrato s2
			     WHERE s2.id_contrato = :idContrato AND s2.estatus = true))
			""", nativeQuery = true)
	List<ServicioContratoModel> obtenerServiciosOrdenadosNative(@Param("idContrato") Long idContrato);

	@Query(value = """
			SELECT SUM(t.monto_total) FROM (

			    SELECT SUM(servicios_bolsa.monto_maximo * :cantidad) AS monto_total
			    FROM (
			        SELECT DISTINCT ON (s.id_grupo_servicio) s.*
			        FROM sisecofi.sscft_servicio_contrato s
			        INNER JOIN sisecofi.sscft_grupo_servicio_contrato gs
			            ON s.id_grupo_servicio = gs.id_grupo_servicio
			        WHERE gs.id_tipo_consumo = 2
			          AND s.id_contrato = :idContrato
			          AND s.ieps = true
			        ORDER BY s.id_grupo_servicio, s.id_servicio_contrato
			    ) AS servicios_bolsa

			    UNION ALL

			    SELECT SUM(sc.monto_maximo * :cantidad) AS monto_total
			    FROM sisecofi.sscft_servicio_contrato sc
			    INNER JOIN sisecofi.sscft_grupo_servicio_contrato gs
			        ON sc.id_grupo_servicio = gs.id_grupo_servicio
			    WHERE gs.id_tipo_consumo = 1
			      AND sc.id_contrato = :idContrato
			      AND sc.ieps = true

			) AS t
			""", nativeQuery = true)
	BigDecimal calcularMontoIeps(@Param("cantidad") BigDecimal cantidad, @Param("idContrato") Long idContrato);

}
