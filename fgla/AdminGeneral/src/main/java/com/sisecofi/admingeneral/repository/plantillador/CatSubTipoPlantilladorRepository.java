package com.sisecofi.admingeneral.repository.plantillador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.CatSubTipoPlantillador;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatSubTipoPlantilladorRepository extends JpaRepository<CatSubTipoPlantillador, Long> {

	List<CatSubTipoPlantillador> findByCatTipoPlantilladorIdTipoPlantillador(Integer idTipoPlantillador);
	
	Optional<CatSubTipoPlantillador> findByIdSubTipoPlantillador(Long id);

}
