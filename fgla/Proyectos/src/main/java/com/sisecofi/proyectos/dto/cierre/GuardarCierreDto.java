package com.sisecofi.proyectos.dto.cierre;

import java.util.List;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.proyectos.model.cierre.CierreModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuardarCierreDto {
	
	private CierreModel cierreModel;
	private List<Archivo> archivos;

}
