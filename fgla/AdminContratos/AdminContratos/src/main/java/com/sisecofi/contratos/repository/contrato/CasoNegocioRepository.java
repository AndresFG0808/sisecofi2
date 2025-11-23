package com.sisecofi.contratos.repository.contrato;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.contratos.model.caso_negocio.CasoNegocioModel;

import java.util.Optional;


public interface CasoNegocioRepository extends JpaRepository<CasoNegocioModel, Integer> {

	Optional <CasoNegocioModel> findByContratoModelIdContratoAndEstatusTrue(Long idContrato);
}
