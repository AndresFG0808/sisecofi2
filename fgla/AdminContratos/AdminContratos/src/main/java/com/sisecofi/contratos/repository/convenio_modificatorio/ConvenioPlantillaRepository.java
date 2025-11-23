package com.sisecofi.contratos.repository.convenio_modificatorio;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;

import java.util.List;
import java.util.Optional;



public interface ConvenioPlantillaRepository extends JpaRepository<ConvenioPlantilla, Long> {

   List<ConvenioPlantilla> findByidConvenioAndEstatusTrue(Long idContrato);

   Optional<ConvenioPlantilla> findByIdConvenioAndIdPlantillaVigenteAndEstatusTrue(Long idConvenio, Integer idPlantilla);
}
