package com.sisecofi.contratos.repository.contrato;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;

import java.util.List;
import java.util.Optional;



public interface ContratoPlantillaRepository extends JpaRepository<ContratoPlantilla, Long> {

   List<ContratoPlantilla> findByIdContratoAndEstatusTrue(Long idContrato);
   
   Optional<ContratoPlantilla> findByIdContratoAndIdPlantillaVigenteAndEstatusTrue(Long idContrato, Integer idPlantilla);

}
