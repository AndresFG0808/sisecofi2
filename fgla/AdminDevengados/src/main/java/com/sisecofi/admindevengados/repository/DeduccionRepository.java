package com.sisecofi.admindevengados.repository;

import java.math.BigDecimal;
import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sisecofi.admindevengados.model.DeduccionesModel;
import org.springframework.data.jpa.repository.Query;

public interface DeduccionRepository extends JpaRepository<DeduccionesModel, Long> {
	
	List<DeduccionesModel> findByIdDictamenAndEstatusTrueAndDictamenEstatusTrue(Long dictamenId);

	List<DeduccionesModel> findByIdDictamenAndEstatusTrueAndDictamenEstatusTrueOrderByIdDeduccionAsc(Long dictamenId);
	
	boolean existsByIdDictamenAndEstatusTrue(Long dictamenId);

	List<DeduccionesModel> findByIdDictamenAndEstatusTrue(Long dictamenId);

	@Query("SELECT SUM(d.monto) FROM DeduccionesModel d WHERE d.idDictamen = :idDictamen")
	BigDecimal sumarMontosPorIdDictamen(@Param("idDictamen") String idDictamen);
}
