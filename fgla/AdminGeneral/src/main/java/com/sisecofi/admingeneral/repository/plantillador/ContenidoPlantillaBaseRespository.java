package com.sisecofi.admingeneral.repository.plantillador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorBaseModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ContenidoPlantillaBaseRespository extends JpaRepository<ContenidoPlantilladorBaseModel, Long> {

	Optional<ContenidoPlantilladorBaseModel> findByCatTipoPlantilladorIdTipoPlantillador(Integer idTipo);

	
}
