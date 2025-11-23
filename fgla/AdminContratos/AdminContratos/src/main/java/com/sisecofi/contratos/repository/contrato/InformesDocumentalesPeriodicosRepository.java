package com.sisecofi.contratos.repository.contrato;

import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import org.springframework.data.jpa.repository.Query;

public interface InformesDocumentalesPeriodicosRepository extends JpaRepository<InformesDocumentalesPeriodicosModel, Long>{
	
	List<InformesDocumentalesPeriodicosModel> findAllByIdContratoAndEstatusTrueOrderByIdPeriodicos(Long idContrato);

	@Query("SELECT e FROM InformesDocumentalesUnicaVezModel e WHERE e.informeDocumental IN :informes AND e.estatus = true AND e.idContrato = :idContrato")
	List<InformesDocumentalesPeriodicosModel> findByInformeDocumentalInAndEstatusTrueAndIdContrato(@Param("informes") List<String> informes, @Param("idContrato") Long idContrato);

	@Modifying
	@Query("UPDATE InformesDocumentalesPeriodicosModel i SET i.estatus = false WHERE i.idContrato = :idContrato")
	void updateEstatusToFalseByIdContrato(@Param("idContrato") Long idContrato);
}
