package com.sisecofi.contratos.repository.contrato;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.contratos.model.caso_negocio.CasoNegocioConvenioModel;

import java.util.Optional;


public interface CasoNegocioConvenioRepository extends JpaRepository<CasoNegocioConvenioModel, Integer> {

	Optional <CasoNegocioConvenioModel> findByConvenioModificatorioModelIdConvenioModificatorioAndEstatusTrue(Long idConvenio);
}
