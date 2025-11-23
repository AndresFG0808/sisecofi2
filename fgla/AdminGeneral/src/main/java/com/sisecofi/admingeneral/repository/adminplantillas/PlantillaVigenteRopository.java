package com.sisecofi.admingeneral.repository.adminplantillas;

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
	
	List<PlantillaVigenteModel> findByNombre(String nombre);
	
	List<PlantillaVigenteModel> findByCatFaseProyectoNombreAndEstadoTrue(String nombreFase);
}
