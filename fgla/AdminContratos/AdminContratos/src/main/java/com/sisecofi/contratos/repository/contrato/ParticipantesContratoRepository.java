package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantesContratoRepository extends JpaRepository<ParticipantesAdministracionModel, Long> {

    List<ParticipantesAdministracionModel> findByIdContratoAndEstatusTrue(Long idContrato);


}
