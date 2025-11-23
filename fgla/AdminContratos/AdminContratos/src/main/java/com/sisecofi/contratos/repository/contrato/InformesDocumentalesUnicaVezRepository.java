package com.sisecofi.contratos.repository.contrato;

import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import org.springframework.data.jpa.repository.Query;

public interface InformesDocumentalesUnicaVezRepository extends JpaRepository<InformesDocumentalesUnicaVezModel, Long> {
	
	List<InformesDocumentalesUnicaVezModel> findAllByIdContratoAndEstatusTrueOrderById(Long idContrato);

	@Query("SELECT e FROM InformesDocumentalesUnicaVezModel e WHERE e.informeDocumental IN :informes AND e.estatus = true AND e.idContrato = :idContrato")
	List<InformesDocumentalesUnicaVezModel> findByInformeDocumentalInAndEstatusTrueAndIdContrato(@Param("informes") List<String> informes, @Param("idContrato") Long idContrato);

	@Modifying
	@Query("UPDATE InformesDocumentalesUnicaVezModel i SET i.estatus = false WHERE i.idContrato = :idContrato")
	void updateEstatusToFalseByIdContrato(@Param("idContrato") Long idContrato);
	
}
