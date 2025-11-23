package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusContrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstatusContratoRepository extends JpaRepository<CatEstatusContrato, Integer> {

    CatEstatusContrato findByIdEstatusContrato(Integer idEstatusContrato);
}
