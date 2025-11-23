package com.sisecofi.reportedocumental.repository.pistas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface TipoMovientoPistaRepository extends JpaRepository<CatTipoMovPistaModel, Integer> {

}
