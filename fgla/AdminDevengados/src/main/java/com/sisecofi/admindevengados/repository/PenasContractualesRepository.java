package com.sisecofi.admindevengados.repository;

import java.math.BigDecimal;
import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import org.springframework.data.jpa.repository.Query;

public interface PenasContractualesRepository extends JpaRepository<PenasContractualesModel, Long> {
	
	List<PenasContractualesModel> findByIdDictamenAndEstatusTrue(Long dictamenId);

	List<PenasContractualesModel> findByIdDictamenAndEstatusTrueOrderByIdPenaContractualAsc(Long dictamenId);
	
	boolean existsByIdDictamenAndEstatusTrue(Long dictamenId);

	List<PenasContractualesModel> findByIdPenaContractualContratoAndEstatusTrue(Long idPenaContractualContrato);

	List<PenasContractualesModel> findByIdAndEstatusTrue(Long id);

	List<PenasContractualesModel> findByIdPeriodicosAndEstatusTrue(Long idPeriodicos);

	List<PenasContractualesModel> findByIdServiciosAndEstatusTrue(Long idServicios);
	
	List<PenasContractualesModel> findByIdAtrasoPrestacionAndEstatusTrue(Long idAtraso);

	@Query("SELECT SUM(d.monto) FROM PenasContractualesModel d WHERE d.idDictamen = :idDictamen")
	BigDecimal sumarMontosPorIdDictamen(@Param("idDictamen") String idDictamen);
	
	
	@Query("SELECT COUNT(p) FROM PenasContractualesModel p WHERE p.dictamen.idContrato = :idContrato AND p.idTipoPenaContractual = :idTipoPenaContractual AND p.estatus = true")
	Long countByDictamenIdContratoAndIdTipoPenaContractualAndEstatusTrue(
	        @Param("idContrato") Long idContrato,
	        @Param("idTipoPenaContractual") Integer idTipoPenaContractual);
	

}
