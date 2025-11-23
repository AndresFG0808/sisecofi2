package com.sisecofi.reportedocumental.repository.dinamico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.dto.reportedinamico.TituloServicioPreveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface TituloServicioProveedorRepository extends JpaRepository<TituloServicioProveedorModel, Long> {

	@Query(nativeQuery = true)
	List<TituloServicioPreveedorDto> findByIdProveedor(List<Long> idProveedor);

}
