package com.sisecofi.admindevengados.repository;

import com.sisecofi.libreria.comunes.model.contratoModificatorio.ContratoModificatorioModel;
import feign.Param;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratoModificatorioRepository extends JpaRepository<ContratoModificatorioModel, Long> {

    ContratoModificatorioModel findByIdContratoModificatorio(Integer idContratoModificatorio);

    @Query("SELECT c FROM ContratoModificatorioModel c WHERE c.idContrato = :idContrato ORDER BY ABS(TIMESTAMPDIFF(DAY, CURRENT_DATE, c.fechaTermino)) ASC")
    List<ContratoModificatorioModel> findClosestContratoByIdAndFechaTermino(@Param("idContrato") Long idContrato);

    Optional<ContratoModificatorioModel> findTopByIdContratoOrderByFechaTermino(Long idContrato);
}
