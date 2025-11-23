package com.sisecofi.contratos.service;

import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantesAdministracionContratoRepository extends JpaRepository<ParticipantesAdministracionModel , Long> {

    List<ParticipantesAdministracionModel> findAllByIdContratoAndEstatusTrue(Long idContrato);

    ParticipantesAdministracionModel findByIdParticipantesAdministracionContrato(Long idParticipantesAdministracionContrato);

    List<ParticipantesAdministracionModel> findByIdResponsabilidadAndIdContratoAndEstatusTrueAndVigenteTrue(Integer idResponsabilidad, Long idContrato);
}
