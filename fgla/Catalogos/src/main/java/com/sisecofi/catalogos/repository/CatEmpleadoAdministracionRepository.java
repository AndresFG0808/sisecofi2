package com.sisecofi.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatEmpleadoAdministracion;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatEmpleadoAdministracionRepository extends JpaRepository<CatEmpleadoAdministracion, Integer> {

	List<CatEmpleadoAdministracion> findByCatAdministracionIdAdministracionOrderByIdEmpleadoAdministracionAsc(Integer idAdministracion);


}
