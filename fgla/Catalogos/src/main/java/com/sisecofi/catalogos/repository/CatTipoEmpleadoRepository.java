package com.sisecofi.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoEmpleado;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatTipoEmpleadoRepository extends JpaRepository<CatTipoEmpleado, Integer> {

	List<CatTipoEmpleado> findByEstatusTrue();


}
