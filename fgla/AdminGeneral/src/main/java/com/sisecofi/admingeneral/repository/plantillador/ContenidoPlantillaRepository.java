package com.sisecofi.admingeneral.repository.plantillador;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;

import java.util.Optional;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ContenidoPlantillaRepository extends JpaRepository<ContenidoPlantilladorModel, Long> {

   Optional<ContenidoPlantilladorModel>  findByplantillaModelPlantilladorModelIdPlantillador(Long idPlantillador);

  Optional<ContenidoPlantilladorModel> findByIdContenidoPlantillador(Long idContenidoPlantillador);
  
  boolean existsByPlantillaModelPlantilladorModelIdPlantillador(Long idPlantillador);

}
