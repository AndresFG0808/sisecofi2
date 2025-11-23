package com.sisecofi.admindevengados.repository;

import java.math.BigDecimal;
import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sisecofi.admindevengados.model.PenasConvencionalesModel;

import org.springframework.data.jpa.repository.Query;

public interface PenasConvencionalesRepository extends JpaRepository<PenasConvencionalesModel, Long> {
	
	List<PenasConvencionalesModel> findByIdDictamenAndEstatusTrue(Long dictamenId);

	List<PenasConvencionalesModel> findByIdDictamenAndEstatusTrueOrderByIdPenaConvencionalAsc(Long dictamenId);
	
	boolean existsByIdDictamenAndEstatusTrue(Long dictamenId);

	@Query("SELECT SUM(d.monto) FROM PenasConvencionalesModel d WHERE d.idDictamen = :idDictamen")
	BigDecimal sumarMontosPorIdDictamen(@Param("idDictamen") String idDictamen);
	

	@Query("SELECT COUNT(p) FROM PenasConvencionalesModel p WHERE p.dictamen.idContrato = :idContrato AND p.idTipoPenaConvencional = :idTipoPenaConvencional AND p.estatus = true")
	Long countByDictamenIdContratoAndIdTipoPenaConvencionalAndEstatusTrue(
	        @Param("idContrato") Long idContrato,
	        @Param("idTipoPenaConvencional") Integer idTipoPenaConvencional);
	
}
