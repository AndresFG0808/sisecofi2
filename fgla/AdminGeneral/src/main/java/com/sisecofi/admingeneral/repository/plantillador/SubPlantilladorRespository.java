package com.sisecofi.admingeneral.repository.plantillador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface SubPlantilladorRespository extends JpaRepository<SubPlantilladorModel, Long> {

	List<SubPlantilladorModel> findByCatTipoPlantilladorIdTipoPlantillador(Integer idTipoPlantillador);

	List<SubPlantilladorModel> findByStatusTrue();
	
	Optional<SubPlantilladorModel> findByNombre(String nombre);

}
