package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConvenioModificatorioRepository extends JpaRepository<ConvenioModificatorioModel, Long> {

	List<ConvenioModificatorioModel> findByIdContratoAndEstatusTrue(Long idContrato);

	Page<List<ConvenioModificatorioModel>> findByIdContratoAndEstatusTrue(Long idContrato, Pageable pageable);

	Optional<ConvenioModificatorioModel> findByNumeroConvenioAndEstatusTrue(String numeroConvenio);

	Optional<ConvenioModificatorioModel> findByIdConvenioModificatorioAndEstatusTrue(Long idConvenio);

	Long countByIdContrato(Long idContrato);

	@Query(value = "SELECT id_convenio_modificatorio " + "FROM sisecofi.sscft_convenio_modificatorio "
			+ "WHERE id_contrato = :idContrato " + "AND id_convenio_modificatorio < :idConvenio "
			+ "ORDER BY id_convenio_modificatorio DESC " + "LIMIT 1", nativeQuery = true)
	Optional<Long> findUltimoIdConvenioByIdContratoAnterior(@Param("idContrato") Long idContrato,
			@Param("idConvenio") Long idConvenio);

}
