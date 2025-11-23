package com.sisecofi.reportedocumental.repository.pistas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ModuloPistaRepository extends JpaRepository<CatModuloPistaModel, Integer> {

}
