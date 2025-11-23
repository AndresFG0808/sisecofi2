package com.sisecofi.reportedocumental.microservicio.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import org.springframework.data.repository.query.Param;

/**
 * @author ayuso2104@gmail.com
 */

public interface ContratoRepository extends JpaRepository<ContratoModel, Long> {

    @Query(nativeQuery = true)
    List<ContratoNombreDto> findByIdEstatusContrato(List<Integer> estatusContrato);

    @Query("SELECT new com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto(c.idContrato, c.nombreCorto) FROM ContratoModel c WHERE c.proyecto.idProyecto = :idProyecto")
    List<ContratoNombreDto> findAllByIdProyecto(@Param("idProyecto") Integer idProyecto);

}
