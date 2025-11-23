package com.sisecofi.admindevengados.repository;

import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConvenioModificatorioRepository extends JpaRepository<ConvenioModificatorioModel, Long> {

    List<ConvenioModificatorioModel> findByIdContratoAndEstatusTrue(Long idContrato);
    
    List<ConvenioModificatorioModel> findByContratoModelAndEstatusTrue(ContratoModel contratoModel);
    
    Optional<ConvenioModificatorioModel> findByNumeroConvenioAndEstatusTrue(String numeroConvenio);
    
    Optional<ConvenioModificatorioModel> findByIdConvenioModificatorioAndEstatusTrue(Long idConvenio);
    
    boolean existsByIdContratoAndEstatusTrue(Long idContrato);
    
}
