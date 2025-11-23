package com.sisecofi.admindevengados.repository;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoLigeroDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<ContratoModel, Integer>, JpaSpecificationExecutor<ContratoModel> {

    Optional<List<ContratoModel>> findAllByEstatusTrue();

   Optional<ContratoModel> findByIdContrato(Long idContrato);

   Optional<ContratoModel> findByIdContratoAndEstatusTrue(Long idContrato);
   
   @Query("SELECT c.nombreCorto FROM ContratoModel c WHERE c.idContrato = :idContrato AND c.estatus = true")
   Optional<String> findNombreCortoByIdContratoAndEstatusTrue(@Param("idContrato") Long idContrato);
   
   @Query("""
		    SELECT new com.sisecofi.libreria.comunes.dto.contrato.ContratoLigeroDto(
		        c.idContrato,
		        c.nombreCorto,
		        d.numeroContrato
		    )
		    FROM ContratoModel c
		    JOIN c.datosGenerales d
		    WHERE c.idContrato = :idContrato
		""")
		Optional<ContratoLigeroDto> findByIdContratoDto(@Param("idContrato") Long idContrato);

   
   List<ContratoModel> findByEstatus(Boolean estatus);
   
   Optional<ContratoModel> findByNombreCortoAndEstatusTrue(String nombreCorto);
   
   Optional<ContratoModel> findByNombreCorto(String nombreCorto);

}
