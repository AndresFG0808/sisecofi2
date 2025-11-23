package com.sisecofi.admingeneral.repository.adminplantillas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CarpetaPlantillaRepository extends JpaRepository<CarpetaPlantillaModel, Integer> {

	@Query("select c from CarpetaPlantillaModel c where c.plantillaVigenteModel.idPlantillaVigente =:idPlantillaVigente order by c.orden ")
	List<CarpetaPlantillaModel> buscarPorIdPlantilla(@Param("idPlantillaVigente") Integer idPlantillaVigente);

	@Query("select c.idCarpetaPlantilla from CarpetaPlantillaModel c where c.nombre=:nombre and  c.plantillaVigenteModel.idPlantillaVigente =:idPlantillaVigente ")
	Integer buscarPorIdNombre(@Param("nombre") String nombre, @Param("idPlantillaVigente") Integer idPlantillaVigente);

	List<CarpetaPlantillaModel> findByPlantillaVigenteModelIdPlantillaVigenteAndNivel(Integer idPlantillaVigente, int nivel);
	
	@Query("SELECT MAX(c.nivel) FROM CarpetaPlantillaModel c WHERE c.plantillaVigenteModel.idPlantillaVigente = :idPlantillaVigente")
	Integer findMaxNivelByPlantillaId(@Param("idPlantillaVigente") Integer idPlantillaVigente);
	
	List<CarpetaPlantillaModel> findByNivelAndPlantillaVigenteModelIdPlantillaVigente(int nivel, Integer idPlantillaVigente);
	
	List<CarpetaPlantillaModel> findByNivelAndPlantillaVigenteModelIdPlantillaVigenteOrderByIdCarpetaPlantillaAsc(int nivel, Integer idPlantillaVigente);
 
	
	Optional<CarpetaPlantillaModel> findByNivelAndPlantillaVigenteModelIdFaseProyectoAndNombre(int nivel, Integer idFase, String nombre);
	
	List<CarpetaPlantillaModel> findByPlantillaVigenteModelCatFaseProyectoNombreAndNivel(String nombreFase, int nivel);
}
