package com.sisecofi.reportedocumental.repository.pistas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.pista.PistaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PistaReporteRepository extends JpaRepository<PistaModel, Long> {

	PistaModel findByIdPistaAndModuloPistaModelIdModuloPistaAndSeccionPistaModelIdSeccionPista(Long idPista,
			Integer idModuloPista, Integer idSeccionPista);

	@Query(value = "SELECT MAX(id_pista) FROM sisecofi.sscfb_pista WHERE id_modulo_pista = :idModuloPista AND id_seccion_pista = :idSeccionPista", nativeQuery = true)
	Long buscarMaximo(@Param("idModuloPista") Integer idModuloPista, @Param("idSeccionPista") Integer idSeccionPista);

	@Query(value = "SELECT MAX(id_pista) FROM sisecofi.sscfb_pista " + "WHERE id_modulo_pista = :idModuloPista "
			+ "AND id_seccion_pista = :idSeccionPista " + "AND id_tipo_mov_pista IN (1, 3, 4)", nativeQuery = true)
	Long buscarMaximoConFiltro(@Param("idModuloPista") Integer idModuloPista,
			@Param("idSeccionPista") Integer idSeccionPista);

	PistaModel findByIdPistaAndModuloPistaModelIdModuloPistaAndSeccionPistaModelIdSeccionPistaAndIdPista(long l,
			Integer idModuloPista, Integer idSeccionPista, Long idPista);

	@Query(value = "SELECT MAX(id_pista) FROM sisecofi.sscfb_pista WHERE id_modulo_pista = :idModuloPista AND id_seccion_pista = :idSeccionPista AND id_pista = :idPista", nativeQuery = true)
	Long buscarMaximo(@Param("idModuloPista") Integer idModuloPista, @Param("idSeccionPista") Integer idSeccionPista,
			@Param("idPista") Long idPista);

	@Query(value = "SELECT * FROM sisecofi.sscfb_pista WHERE id_pista = :idPista AND id_modulo_pista = :idModuloPista AND id_seccion_pista = :idSeccionPista", nativeQuery = true)
	PistaModel buscarPista(@Param("idPista") Long idPista, @Param("idModuloPista") Integer idModuloPista,
			@Param("idSeccionPista") Integer idSeccionPista);

	@Query(value = "SELECT * FROM sisecofi.sscfb_pista WHERE id_pista = :idPista", nativeQuery = true)
	Optional<PistaModel> findByIdNativeQuery(@Param("idPista") Long idPista);

	@Query(value = "SELECT MAX(id_pista) FROM sisecofi.sscfb_pista " + "WHERE id_modulo_pista = :idModuloPista "
			+ "AND id_seccion_pista = :idSeccionPista " + "AND id_pista < :idPista", nativeQuery = true)
	Long buscarMaximoMenor(@Param("idModuloPista") Integer idModuloPista,
			@Param("idSeccionPista") Integer idSeccionPista, @Param("idPista") Long idPista);
}
