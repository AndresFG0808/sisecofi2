package com.sisecofi.admindevengados.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantesContratoRepository extends JpaRepository<ParticipantesAdministracionModel, Long> {

    List<ParticipantesAdministracionModel> findByIdContratoAndEstatusTrueAndVigenteTrue(Long idContrato);
    
    @Query("SELECT p.idContrato, p.catResponsabilidad.nombre, p.idUser " +
    	       "FROM ParticipantesAdministracionModel p " +
    	       "WHERE p.idContrato IN :idContratos AND p.estatus = TRUE")
    	List<Object[]> findParticipantesByIdContratoIn(@Param("idContratos") List<Long> idContratos);



}
