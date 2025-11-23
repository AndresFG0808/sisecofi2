package com.sisecofi.proyectos.dto;

import java.util.List;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class ProyectoUsuarioDto extends ProyectoNombreDto {

	private List<Usuario> usuarios;
	private List<Usuario> usuariosAsignados;

}
