package com.sisecofi.contratos.repository.contrato;

import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import org.springframework.data.jpa.repository.Query;


public interface NivelesServicioSlaRepository extends JpaRepository<NivelesServicioSLAModel, Long>{
	List<NivelesServicioSLAModel> findAllByIdContratoAndEstatusTrueOrderByIdServiciosSla(Long id);

	@Query("SELECT e FROM NivelesServicioSLAModel e WHERE e.sla IN :informes AND e.estatus = true AND e.idContrato = :idContrato")
	List<NivelesServicioSLAModel> findByInformeDocumentalInAndEstatusTrueAndIdContrato(@Param("informes") List<String> informes, @Param("idContrato") Long idContrato);

	@Modifying
	@Query("UPDATE NivelesServicioSLAModel i SET i.estatus = false WHERE i.idContrato = :idContrato")
	void updateEstatusToFalseByIdContrato(@Param("idContrato") Long idContrato);
}
