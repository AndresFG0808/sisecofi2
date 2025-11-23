package com.sisecofi.libreria.comunes.dto.plantilla;

import java.util.List;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class PlantillaDto <T>{
	
	private CatFaseProyecto catFaseProyecto;
	private Integer idFase;
	private PlantillaVigenteModel plantillaVigenteModel;
	private List<T> carpetas;
}