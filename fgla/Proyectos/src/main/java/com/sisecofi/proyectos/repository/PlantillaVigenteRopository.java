package com.sisecofi.proyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import java.util.List;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PlantillaVigenteRopository extends JpaRepository<PlantillaVigenteModel, Integer> {

	List<PlantillaVigenteModel> findByIdFaseProyectoAndEstado(Integer idFaseProyecto, boolean estado);

	PlantillaVigenteModel findByIdPlantillaVigente(Integer idPlantillaVigente);
}
