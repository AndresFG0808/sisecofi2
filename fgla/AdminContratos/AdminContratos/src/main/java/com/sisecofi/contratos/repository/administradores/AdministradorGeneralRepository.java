package com.sisecofi.contratos.repository.administradores;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdministradorGeneralRepository extends JpaRepository<CatAdministradorGeneral, Integer> {

	List<CatAdministradorGeneral> findByCatAdmonGeneralIdAdmonGeneral(Integer idGeneral);
}
