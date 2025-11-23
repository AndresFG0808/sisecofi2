package com.sisecofi.contratos.repository.administradores;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdministradorAdministracionRepository extends JpaRepository<CatAdministradorAdministracion, Integer> {

	
	List<CatAdministradorAdministracion> findByCatAdministracionCatAdmonCentralIdAdmonCentral(Integer idCentral);
}
