package com.sisecofi.admingeneral.repository.adminpistas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.pista.PistaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PistasRepository extends JpaRepository<PistaModel, Long> {

}
