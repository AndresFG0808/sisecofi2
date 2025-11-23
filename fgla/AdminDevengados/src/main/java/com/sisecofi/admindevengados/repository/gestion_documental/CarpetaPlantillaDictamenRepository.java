package com.sisecofi.admindevengados.repository.gestion_documental;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.CarpetaPlantillaDictamenModel;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarpetaPlantillaDictamenRepository extends JpaRepository<CarpetaPlantillaDictamenModel, Integer> {

	List<CarpetaPlantillaDictamenModel> findByNivelAndIdDictamenAndCarpetaBaseEstatusTrue(int nivel, Long idDictamen);

	List<CarpetaPlantillaDictamenModel> findByIdDictamen(Long idDictamen);
	

}
