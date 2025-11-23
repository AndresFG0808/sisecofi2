package com.sisecofi.admindevengados.repository;

import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VigenciaMontosRepository extends JpaRepository<VigenciaMontosModel, Long> {
	Optional<VigenciaMontosModel> findByIdContratoAndEstatusTrue(Long idContrato);

	Optional<VigenciaMontosModel> findByIdVigenciaMontoAndEstatusTrue(Long idVigencia);

	List<VigenciaMontosModel> findByEstatusTrue();
	
	@Query("SELECT v.idTipoMoneda FROM VigenciaMontosModel v WHERE v.idContrato = :idContrato AND v.estatus = true")
	Optional<Integer> findIdTipoMonedaByIdContratoAndEstatusTrue(@Param("idContrato") Long idContrato);
	
	@Query("SELECT v.catIeps.porcentaje FROM VigenciaMontosModel v WHERE v.idContrato = :idContrato AND v.estatus = true")
    Optional<String> findPorcentajeIepsByIdContratoAndEstatusTrue(@Param("idContrato") Long idContrato);

}