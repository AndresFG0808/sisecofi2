package com.sisecofi.reportedocumental.repository.controldoc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.dto.reportecontroldoc.PlantillaVigenteDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PlantillaVigenteRepository extends JpaRepository<PlantillaVigenteModel, Integer> {

	@Query(nativeQuery = true)
	List<PlantillaVigenteDto> findByFaseProyecto(Integer idFaseProyecto);

}
