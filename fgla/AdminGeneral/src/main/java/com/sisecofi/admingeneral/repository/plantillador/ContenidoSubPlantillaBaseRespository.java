package com.sisecofi.admingeneral.repository.plantillador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.admingeneral.model.plantillador.ContenidoSubPlantilladorBaseModel;


public interface ContenidoSubPlantillaBaseRespository extends JpaRepository<ContenidoSubPlantilladorBaseModel, Long> {

	Optional<ContenidoSubPlantilladorBaseModel> findByCatTipoPlantilladorIdSubTipoPlantillador(Long idTipo);

}
