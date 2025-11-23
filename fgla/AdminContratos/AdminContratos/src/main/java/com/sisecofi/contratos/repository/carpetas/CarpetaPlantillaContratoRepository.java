package com.sisecofi.contratos.repository.carpetas;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarpetaPlantillaContratoRepository extends JpaRepository<CarpetaPlantillaContratoModel, Integer> {

	List<CarpetaPlantillaContratoModel> findByNivelAndContratoPlantilla(int nivel, ContratoPlantilla asociacion);
	
	List<CarpetaPlantillaContratoModel> findByNivelAndContratoPlantillaIn(int nivel, List<ContratoPlantilla> asociaciones);
	
	  List<CarpetaPlantillaContratoModel> findByContratoPlantillaPlantillaVigenteModelCatFaseProyectoAndNivelAndEstatusAndContratoPlantillaIn(
		       CatFaseProyecto catFaseProyecto, int nivel, boolean estatus, List <ContratoPlantilla> asociaciones);
	
	List<CarpetaPlantillaContratoModel> findByContratoPlantillaInAndEstatusCarpetaTrue(List<ContratoPlantilla> asociaciones);
	
	List<CarpetaPlantillaContratoModel> findByContratoPlantillaIdContratoAndNivelAndCarpetaBaseEstatusTrueOrderByIdCarpetaPlantillaContrato(Long idContrato, int nivel);

}
