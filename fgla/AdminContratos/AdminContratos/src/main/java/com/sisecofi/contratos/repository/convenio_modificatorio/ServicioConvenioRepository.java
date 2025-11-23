package com.sisecofi.contratos.repository.convenio_modificatorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;

public interface ServicioConvenioRepository extends JpaRepository<ServicioConvenioModel, Long>, JpaSpecificationExecutor<ServicioConvenioModel> {

    List<ServicioConvenioModel> findByIdConvenioModificatorioAndServicioBaseEstatusTrue(Long idConvenio);

   Optional <ServicioConvenioModel> findByIdConvenioModificatorioAndServicioBase(Long idConvenio, ServicioContratoModel servicioBase);
   
   
   List<ServicioConvenioModel> findByServicioBaseIdGrupoServicioAndServicioBaseEstatusTrueAndIdConvenioModificatorio(Long idGrupoServicio, Long idConvenio);

   @Modifying
   @Query("UPDATE ServicioConvenioModel e SET e.primeraVez = false WHERE e.primeraVez = true")
   int actualizarPrimeraVez();
}
