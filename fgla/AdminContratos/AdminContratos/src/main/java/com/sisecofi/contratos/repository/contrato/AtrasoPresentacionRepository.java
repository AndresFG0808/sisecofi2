package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AtrasoPresentacionRepository extends JpaRepository<AtrasoPrestacionModel, Long> {

    AtrasoPrestacionModel findByIdAtrasoPrestacion(Long idAtrasoPresentacion);

    Optional<List<AtrasoPrestacionModel>>  findByEstatusTrue();

    Optional<List<AtrasoPrestacionModel>> findAllByIdContratoAndEstatusTrueOrderByIdAtrasoPrestacion(Long idContrato);

    @Query("SELECT e FROM AtrasoPrestacionModel e WHERE e.penasDeducciones IN :informes AND e.estatus = true AND e.idContrato = :idContrato")
    List<AtrasoPrestacionModel> findByPenasDeduccionesInAndEstatusTrueAndIdContrato(@Param("informes") List<String> informes, @Param("idContrato") Long idContrato);

    @Modifying
	@Query("UPDATE AtrasoPrestacionModel i SET i.estatus = false WHERE i.idContrato = :idContrato")
	void updateEstatusToFalseByIdContrato(@Param("idContrato") Long idContrato);
}
