package com.sisecofi.libreria.comunes.util.sesion;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.UsernameToken;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa Session, para regresar el usuario de la sesión
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionImpl implements Session {

	private final UsuarioRepository repository;

	/**
	 * Regresa el nombre del usuaurio de la sesion
	 */
	@Override
	public UsernameToken retornarNombreUsuario() {
		try {
			return (UsernameToken) SecurityContextHolder.getContext().getAuthentication();
		} catch (ClassCastException e) {
	        log.error("Error al convertir el objeto de autenticación a UsernameToken:");
	    } catch (NullPointerException e) {
	        log.warn("No se encontró un contexto de autenticación válido");
	    } catch (Exception e) {
	        log.error("Error inesperado al obtener el usuario del contexto:");
	    }
	    return null;
	}

	/**
	 * regresa el usuario de la sesion
	 */
	@Override
	public Optional<Usuario> retornarUsuario() {

		String user = retornarNombreUsuario().getPrincipal().toString();
		return repository.findByUserNameOrRfcLargo(user);
	}

}
