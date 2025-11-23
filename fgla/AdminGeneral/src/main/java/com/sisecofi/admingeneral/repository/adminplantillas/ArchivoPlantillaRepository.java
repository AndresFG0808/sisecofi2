package com.sisecofi.admingeneral.repository.adminplantillas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface ArchivoPlantillaRepository extends JpaRepository<ArchivoPlantillaModel, Integer> {

	@Query("select a from ArchivoPlantillaModel a where a.carpetaPlantillaModel.idCarpetaPlantilla = :idCarpetaPlantilla order by a.orden ")
	List<ArchivoPlantillaModel> buscarIdCarpetaPlantilla(@Param("idCarpetaPlantilla") Integer idCarpetaPlantilla);

	List<ArchivoPlantillaModel> findByCarpetaPlantillaModelIdCarpetaPlantilla(Integer idCarpetaPlantilla);
}
