package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.GrupoServiciosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GrupoServicioContratoRepository extends JpaRepository<GrupoServiciosModel, Long> {

    GrupoServiciosModel findByIdGrupoServicioAndEstatusTrue(Long idGrupoServicio);

    List<GrupoServiciosModel> findByEstatusTrue();

    List<GrupoServiciosModel> findByIdContratoAndEstatusTrue(Long idContrato);
    
    @Modifying
    @Query("UPDATE GrupoServiciosModel g SET g.estatus = false WHERE g.idGrupoServicio IN :idsGrupoServicio")
    void updateEstatusFalseByIds(@Param("idsGrupoServicio") List<Long> idsGrupoServicio);
    
    @Query("SELECT g.idContrato FROM GrupoServiciosModel g WHERE g.idGrupoServicio = :idGrupo")
    Long findIdContratoByGrupo(@Param("idGrupo") Long idGrupo);
    
    Optional<GrupoServiciosModel> findByGrupoAndEstatusTrue(String grupo);
    
    Optional<GrupoServiciosModel> findByGrupoAndIdContratoAndEstatusTrue(String grupo, Long idContrato);


}
