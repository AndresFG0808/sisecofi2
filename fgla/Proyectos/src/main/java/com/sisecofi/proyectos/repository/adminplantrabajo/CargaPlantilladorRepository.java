package com.sisecofi.proyectos.repository.adminplantrabajo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;

public interface CargaPlantilladorRepository extends JpaRepository<PlantilladorModel, Long> {
    
    List<PlantilladorModel> findByCatTipoPlantilladorIdTipoPlantillador(Integer idTipoPlantillador);

    List<PlantilladorModel> findByNombreContainingIgnoreCase(String nombre);

    Optional<PlantilladorModel> findByIdPlantillador(Long idPlantillador);




    


}
