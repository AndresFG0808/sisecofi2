package com.sisecofi.contratos.repository.reintegros;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;

import feign.Param;

@Repository
public interface ReintegrosRepository  extends JpaRepository<ReintegrosAsociadosModel, Long>{

    

    Optional<ReintegrosAsociadosModel> findByIdReintegrosAsociadosAndEstatusTrue(Long idReintegrosAsociados);


    // consulta para  buscar reintegros por idContrato y estatus = true
    @Query("SELECT ra FROM ReintegrosAsociadosModel ra JOIN ra.contratoModel sc JOIN ra.tipoReintegro str " +
            "WHERE ra.estatus = true AND sc.idContrato = :idContrato ORDER BY ra.ordenContrato ASC")
    List<ReintegrosAsociadosModel> findReintegrosByIdContrato(@Param("idContrato") Long idContrato);

    List<ReintegrosAsociadosModel> findByIdContratoAndEstatusTrue(Long idContrato);
    
    List<ReintegrosAsociadosModel> findByIdContratoAndEstatusTrueOrderByIdReintegrosAsociadosAsc(Long idContrato);


    @Query("SELECT MAX(ra.ordenContrato) FROM ReintegrosAsociadosModel ra")
    Integer findMaxOrdenContrato();

    @Query("SELECT COALESCE(MAX(ra.ordenContrato), 0) FROM ReintegrosAsociadosModel ra WHERE ra.contratoModel.idContrato = :idContrato")
    Integer findMaxOrdenContratoByIdContrato(@Param("idContrato") Long idContrato);


    @Query("SELECT ra FROM ReintegrosAsociadosModel ra WHERE ra.contratoModel.idContrato = :idContrato AND ra.estatus = true ORDER BY ra.ordenContrato ASC")
    List<ReintegrosAsociadosModel> findByContratoModelIdContrato(@Param("idContrato") Long idContrato);





}
