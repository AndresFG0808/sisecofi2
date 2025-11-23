package com.sisecofi.contratos.repository.carpetas;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarpetaPlantillaConvenioRepository extends JpaRepository<CarpetaPlantillaConvenioModel, Integer> {

	List<CarpetaPlantillaConvenioModel> findByNivelAndConvenioPlantilla(int nivel, ConvenioPlantilla asociacion);
	
	List<CarpetaPlantillaConvenioModel> findByNivelAndConvenioPlantillaIn(int nivel, List<ConvenioPlantilla> asociaciones);
	
	  List<CarpetaPlantillaConvenioModel> findByConvenioPlantillaPlantillaVigenteModelCatFaseProyectoAndNivelAndEstatusAndConvenioPlantillaIn(
		       CatFaseProyecto catFaseProyecto, int nivel, boolean estatus, List <ConvenioPlantilla> asociaciones);
	
	List<CarpetaPlantillaConvenioModel> findByConvenioPlantillaInAndEstatusCarpetaTrue(List<ConvenioPlantilla> asociaciones);
	
	List<CarpetaPlantillaConvenioModel> findByConvenioPlantillaIdConvenioAndNivelAndCarpetaBaseEstatusTrueOrderByIdCarpetaPlantillaConvenio(Long idConvenio, int nivel);

}
