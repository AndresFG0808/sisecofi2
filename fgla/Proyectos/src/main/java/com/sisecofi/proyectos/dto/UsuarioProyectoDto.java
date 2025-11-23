package com.sisecofi.proyectos.dto;

import java.util.List;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UsuarioProyectoDto extends Usuario {

	private List<ProyectoModel> proyectos;
	private List<ProyectoModel> proyectosAsignados;

}
