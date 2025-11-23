package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstimacionRepository extends JpaRepository<EstimacionModel, Long> {

    List<EstimacionModel> findByIdContratoAndEstatusTrue(Long idContrato);
    
    Optional<List<EstimacionModel>> findByIdContratoAndEstatusTrueAndCatEstatusEstimacionNombreNotLike(Long idContrato, String nombre);

    List<EstimacionModel> findByIdContratoAndEstatusTrueAndCatEstatusEstimacionNombreNotIn(Long idContrato, List<String> estatus);

}
