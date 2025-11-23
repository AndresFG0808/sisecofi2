package com.sisecofi.admingeneral.repository.plantillador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.ContenidoSubPlantilladorModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ContenidoSubPlantilladorRespository extends JpaRepository<ContenidoSubPlantilladorModel, Long> {

	ContenidoSubPlantilladorModel findBySubPlantilladorDatosModelIdSubPlantilladorDatos(Long idSubplantillador);
	
	
	List<ContenidoSubPlantilladorModel> findBySubPlantilladorDatosModelSubPlantilladorModelIdSubPlantillador(Long idSubPlantillador);
	
}
