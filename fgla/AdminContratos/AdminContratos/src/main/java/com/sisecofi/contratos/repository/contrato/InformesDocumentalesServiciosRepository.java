package com.sisecofi.contratos.repository.contrato;

import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import org.springframework.data.jpa.repository.Query;

public interface InformesDocumentalesServiciosRepository  extends JpaRepository<InformesDocumentalesServiciosModel, Long>{
	List<InformesDocumentalesServiciosModel> findAllByIdContratoAndEstatusTrueOrderByIdServicios(Long id);

	@Query("SELECT e FROM InformesDocumentalesServiciosModel e WHERE e.informeDocumental IN :informes AND e.estatus = true AND e.idContrato = :idContrato")
	List<InformesDocumentalesServiciosModel> findByInformeDocumentalInAndEstatusTrueAndIdContrato(@Param("informes") List<String> informes, @Param("idContrato") Long idContrato);

	@Modifying
	@Query("UPDATE InformesDocumentalesServiciosModel i SET i.estatus = false WHERE i.idContrato = :idContrato")
	void updateEstatusToFalseByIdContrato(@Param("idContrato") Long idContrato);
}
