package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;



public interface PenaContractualContratoRepository extends JpaRepository<PenaContractualContratoModel, Long> {

  List<PenaContractualContratoModel> findByIdContratoAndEstatusTrueOrderByIdPenaContractualContrato(Long idContrato);
  
  Optional<PenaContractualContratoModel> findByIdPenaContractualContrato(Long idPenaContractualContrato);

  @Query("SELECT e FROM PenaContractualContratoModel e WHERE e.informeDocumentoConcepto IN :informes AND e.estatus = true AND e.idContrato = :idContrato")
  List<PenaContractualContratoModel> findByInformeDocumentalInAndEstatusTrueAndIdContrato(@Param("informes") List<String> informes, @Param("idContrato") Long idContrato);

  @Modifying
	@Query("UPDATE PenaContractualContratoModel i SET i.estatus = false WHERE i.idContrato = :idContrato")
	void updateEstatusToFalseByIdContrato(@Param("idContrato") Long idContrato);
}
