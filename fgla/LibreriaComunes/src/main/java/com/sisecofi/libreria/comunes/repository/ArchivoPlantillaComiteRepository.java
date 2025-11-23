package com.sisecofi.libreria.comunes.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;

import java.util.List;

public interface ArchivoPlantillaComiteRepository extends JpaRepository<ArchivoPlantillaComiteModel, Integer> {

   List<ArchivoPlantillaComiteModel> findByIdAsociacionComiteProyectoAndEstatusTrue(Integer idAsociacionComite);

   ArchivoPlantillaComiteModel findByIdAndIdAsociacionComiteProyectoAndEstatusTrue (Integer idArchivoPlantilla, Integer idAspciacion);

   ArchivoPlantillaComiteModel findByIdArchivoPlantillaAndIdAsociacionComiteProyectoAndEstatusTrue(Integer idArchivoPlantilla, Integer idAsociacion);
   @Query("SELECT a.ruta FROM ArchivoPlantillaComiteModel a WHERE a.id= :idArchivoPlantillaComite")
   List<String> findRutaByIdArchivoPlantillaComite(@Param("idArchivoPlantillaComite") Integer idArchivoPlantillaComite);

   @Query("SELECT a.ruta FROM ArchivoPlantillaComiteModel a WHERE a.idAsociacionComiteProyecto = :idAsociacionComiteProyecto")
   List<String> findRutaByIdAsociacionComiteProyecto(@Param("idAsociacionComiteProyecto") Integer idAsociacionComiteProyecto);

   @Query("SELECT a.ruta FROM ArchivoPlantillaComiteModel a WHERE a.idAsociacionComiteProyecto = :idAsociacionComiteProyecto AND a.estatus = true")
   List<String> findRutaByIdAsociacionComiteProyectoAndEstatusTrue(@Param("idAsociacionComiteProyecto") Integer idAsociacionComiteProyecto);
   
   List<Archivo> findByAsociasionComitePlantillaModelComiteProyectoModelIdProyectoAndEstatusTrue(Long idProyecto);
}
