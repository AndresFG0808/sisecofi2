package com.sisecofi.admingeneral.service.adminusuarios;

import java.util.List;

import com.sisecofi.admingeneral.dto.adminusuarios.BusquedaUsuarioDto;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface UsuariosService {

	List<Usuario> obtenerUsuario(BusquedaUsuarioDto busquedaUsuarioDto);

	List<Usuario> buscarUsuarioDirectorioActivo(BusquedaUsuarioDto busquedaUsuarioDto);

	Usuario guardarUsuario(Usuario usuario);

	boolean guardarUsuarios(List<Usuario> usuario);

	byte[] obtenerUsuarioReporte(BusquedaUsuarioDto busquedaUsuarioDto);
	
	boolean guardarPista();
}
