package com.sisecofi.contratos.repository.convenio_modificatorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sisecofi.contratos.model.caso_negocio.CasoNegocioServicioConvenioModel;

public interface ServicioCasoConvenioRepository extends JpaRepository<CasoNegocioServicioConvenioModel, Long>, JpaSpecificationExecutor<CasoNegocioServicioConvenioModel> {

  List<CasoNegocioServicioConvenioModel> findByCasoNegocioConvenioModificatorioModelIdConvenioModificatorioOrderByServicioConvenioServicioBaseOrden(Long idCaso);
}
