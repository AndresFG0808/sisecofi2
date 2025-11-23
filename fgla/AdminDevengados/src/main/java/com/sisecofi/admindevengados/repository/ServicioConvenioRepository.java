package com.sisecofi.admindevengados.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;

public interface ServicioConvenioRepository extends JpaRepository<ServicioConvenioModel, Long>, JpaSpecificationExecutor<ServicioConvenioModel> {

    List<ServicioConvenioModel> findByIdConvenioModificatorioAndServicioBaseEstatusTrue(Long idConvenio);

   Optional <ServicioConvenioModel> findByIdConvenioModificatorioAndServicioBase(Long idConvenio, ServicioContratoModel servicioBase);
   
   List<ServicioConvenioModel> findByConvenioNumeroConvenioAndServicioBaseEstatusTrue(String numeroConvenio);
}
