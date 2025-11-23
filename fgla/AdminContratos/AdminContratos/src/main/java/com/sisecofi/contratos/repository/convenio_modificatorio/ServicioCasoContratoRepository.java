package com.sisecofi.contratos.repository.convenio_modificatorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sisecofi.contratos.model.caso_negocio.CasoNegocioServicioModel;

public interface ServicioCasoContratoRepository extends JpaRepository<CasoNegocioServicioModel, Long>, JpaSpecificationExecutor<CasoNegocioServicioModel> {

  List<CasoNegocioServicioModel> findByCasoNegocioContratoModelIdContratoOrderByServicioContratoOrden(Long idCaso);
}
