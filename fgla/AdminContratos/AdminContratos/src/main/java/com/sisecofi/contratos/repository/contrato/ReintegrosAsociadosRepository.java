package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReintegrosAsociadosRepository extends JpaRepository<ReintegrosAsociadosModel, Long> {

    List<ReintegrosAsociadosModel> findByIdContratoAndEstatusTrue(Long idContrato);

}
