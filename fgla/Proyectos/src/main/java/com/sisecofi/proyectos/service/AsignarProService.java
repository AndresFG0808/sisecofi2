package com.sisecofi.proyectos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.proyectos.dto.ProyectoUsuarioDto;
import com.sisecofi.proyectos.dto.UsuarioProyectoDto;

public interface AsignarProService {

	List<ProyectoNombreDto> obtenerProyectosNombreCorto(BaseCatalogoModel catalogoModel);

	ProyectoUsuarioDto buscarProyectoUsuario(ProyectoNombreDto dto);

	boolean guardarUsuariosAsignados(ProyectoUsuarioDto dto);

	List<Usuario> buscarUsuarios();

	UsuarioProyectoDto buscarUsuarioProyecto(Usuario usuario);

	boolean guardarProyectoUsuario(UsuarioProyectoDto dto);

	byte[] exportarProyectosUsuarios(ProyectoNombreDto dto);

}
