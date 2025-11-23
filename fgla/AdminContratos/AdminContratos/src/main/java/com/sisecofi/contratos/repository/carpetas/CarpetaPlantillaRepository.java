package com.sisecofi.contratos.repository.carpetas;

import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarpetaPlantillaRepository extends JpaRepository<CarpetaPlantillaModel, Integer> {

	List<CarpetaPlantillaModel> findByNivelAndPlantillaVigenteModelIdPlantillaVigente(int nivel, Integer idPlantillaVigente);

}
