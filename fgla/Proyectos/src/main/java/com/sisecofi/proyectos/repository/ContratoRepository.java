package com.sisecofi.proyectos.repository;

import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<ContratoModel, Integer>, JpaSpecificationExecutor<ContratoModel> {

    Optional<List<ContratoModel>> findAllByEstatusTrue();

   Optional<ContratoModel> findByIdContrato(Long idContrato);

   Optional<ContratoModel> findByIdContratoAndEstatusTrue(Long idContrato);

   
   List<ContratoModel> findByEstatus(Boolean estatus);
   
   Optional<ContratoModel> findByNombreCortoAndEstatusTrue(String nombreCorto);
   
   Optional<ContratoModel> findByNombreCorto(String nombreCorto);

   Boolean existsByNombreCortoAndNombreContratoAndEstatusTrue(String nombreCorto, String nombreContrato);
   
   List<ContratoModel> findByEstatusTrueAndCatEstatusContratoNombreNotInAndProyectoIdProyecto(
		    List<String> nombres, Long idProyecto);
   
   Long countByEstatusTrueAndCatEstatusContratoNombreNotInAndProyectoIdProyecto(
		    List<String> nombres, Long idProyecto);
   
   

}
