package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.DatosGeneralesContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatosGeneralesContratoRepository extends JpaRepository<DatosGeneralesContratoModel, Long> {

    DatosGeneralesContratoModel findByIdContratoAndEstatusTrue(Long idContrato);
}
