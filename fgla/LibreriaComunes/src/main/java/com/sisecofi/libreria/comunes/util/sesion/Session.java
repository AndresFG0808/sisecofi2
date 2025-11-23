package com.sisecofi.libreria.comunes.util.sesion;

import java.util.Optional;

import com.sisecofi.libreria.comunes.dto.UsernameToken;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;

/**
 * Interfaz donde te regresa el usuario que lleva el request
 * 
 * @author ayuso2104@gmail.com
 *
 */
public interface Session {

	UsernameToken retornarNombreUsuario();

	Optional<Usuario> retornarUsuario();

}
