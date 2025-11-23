package com.sisecofi.libreria.comunes.repository.cierre;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ArchivoPlantillaReintegroRepository extends JpaRepository<ArchivoPlantillaReintegroModel, Integer> {
   
	
	List <ArchivoPlantillaReintegroModel> findByCarpetaPlantillaModelReintegroContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(Long idProyecto);
}
 