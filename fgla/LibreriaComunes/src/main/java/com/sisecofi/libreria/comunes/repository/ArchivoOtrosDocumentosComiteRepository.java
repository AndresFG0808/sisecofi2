package com.sisecofi.libreria.comunes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;

import java.util.List;
import java.util.Optional;

public interface ArchivoOtrosDocumentosComiteRepository extends JpaRepository<ArchivoOtrosDocumentosComiteModel, Integer> {

    List<ArchivoOtrosDocumentosComiteModel> findByIdComiteProyectoAndEstatusTrue(Integer idComiteProyecto);

    List<ArchivoOtrosDocumentosComiteModel> findByIdComiteProyectoAndEstatusTrueAndIdCarpetaPlantillaNull(Integer idComiteProyecto);

    List<ArchivoOtrosDocumentosComiteModel> findByIdCarpetaPlantillaAndIdComiteProyectoAndEstatusTrue(Integer idCarpetaPlantillaComite, Integer idComiteProyecto);

    List<ArchivoOtrosDocumentosComiteModel> findByIdComiteProyectoAndEstatusTrueAndOtrosDocumentosInternoTrue(Integer idCarpetaPlantillaComite);

    List<ArchivoOtrosDocumentosComiteModel> findByIdComiteProyectoAndEstatusTrueAndOtrosDocumentosInternoFalse(Integer idCarpetaPlantillaComite);
    
    List<Archivo> findByComiteProyectoModel_IdProyectoAndEstatusTrue(Long idProyecto);

    Optional<ArchivoOtrosDocumentosComiteModel> findByIdAndEstatusTrue(Integer id);
}
