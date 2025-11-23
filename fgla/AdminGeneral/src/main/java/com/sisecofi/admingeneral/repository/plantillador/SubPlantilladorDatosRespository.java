package com.sisecofi.admingeneral.repository.plantillador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorDatosModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface SubPlantilladorDatosRespository extends JpaRepository<SubPlantilladorDatosModel, Long> {

	Optional<SubPlantilladorDatosModel> findByNombre(String nombre);
}
