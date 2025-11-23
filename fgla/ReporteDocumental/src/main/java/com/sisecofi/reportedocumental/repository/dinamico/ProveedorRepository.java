package com.sisecofi.reportedocumental.repository.dinamico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProveedorRazonSocialDto;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ProveedorRepository extends JpaRepository<ProveedorModel, Long> {

	@Query(nativeQuery = true)
	List<ProveedorRazonSocialDto> findByEstatus(boolean estatus);

}
