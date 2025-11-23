package com.sisecofi.contratos.repository.carpetas;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.CarpetaPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarpetaPlantillaReintegroRepository extends JpaRepository<CarpetaPlantillaReintegroModel, Integer> {

	List<CarpetaPlantillaReintegroModel> findByNivelAndReintegro(int nivel, ReintegrosAsociadosModel reintegro);

	boolean existsByReintegroIdReintegrosAsociados(Long idReintegrosAsociados);
	
	boolean existsByDescripcion(String descripcion);

}
