package com.sisecofi.libreria.comunes.repository;

import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArchivoPlantillaVigenteRepository extends JpaRepository<ArchivoPlantillaModel, Integer> {

	
	@Query("SELECT a FROM ArchivoPlantillaModel a WHERE a.carpetaPlantillaModel.plantillaVigenteModel.idPlantillaVigente = :idPlantillaVigente")
    List<ArchivoPlantillaModel> findByIdPlantillaVigente(@Param("idPlantillaVigente") Integer idPlantillaVigente);
	
}
