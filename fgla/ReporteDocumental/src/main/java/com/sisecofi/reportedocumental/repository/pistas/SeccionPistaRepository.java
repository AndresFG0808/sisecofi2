package com.sisecofi.reportedocumental.repository.pistas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface SeccionPistaRepository extends JpaRepository<CatSeccionPistaModel, Integer> {

	List<CatSeccionPistaModel> findByCatModuloPistaModelIdModuloPista(Integer idModulo);

}
