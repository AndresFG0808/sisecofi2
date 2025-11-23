package com.sisecofi.libreria.comunes.repository.cierre;




import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArchivoPlantillaDictamenRepository extends JpaRepository<ArchivoPlantillaDictamenModel, Integer> {

	Optional<Archivo> findByRuta(String ruta);
	
	@Query("""
		    SELECT a FROM ArchivoPlantillaDictamenModel a
		    LEFT JOIN FacturaModel fp ON fp.archivoPdf = a OR fp.archivoXml = a
		    LEFT JOIN NotaCreditoModel fx ON fx.archivoPdf = a OR fx.archivoXml = a
		    WHERE a.ruta = :path
		      AND (
		            (fp IS NOT NULL AND fp.catEstatusFactura.nombre <> 'Cancelado')
		            OR (fx IS NOT NULL AND fx.catEstatusNotaCredito.nombre <> 'Cancelada')
		            OR (fp IS NULL AND fx IS NULL)
		      )
		""")
		Optional<Archivo> findByPathAndFacturaStatusNotCancelled(@Param("path") String path);


	
	Optional<ArchivoPlantillaDictamenModel> findByNombreContainingAndCarpetaPlantillaModelIdDictamen(String nombre, Long idDictamen);
	
	List<ArchivoPlantillaDictamenModel> findByCarpetaPlantillaModelDictamenContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(Long idProyecto);

	Optional<ArchivoPlantillaDictamenModel> findByDescripcionContainingAndCarpetaPlantillaModelIdDictamen(String descripcion, Long idDictamen);
}
