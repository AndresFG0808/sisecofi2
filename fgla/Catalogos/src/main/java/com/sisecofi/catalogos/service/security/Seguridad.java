package com.sisecofi.catalogos.service.security;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.sesion.Session;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
public class Seguridad extends SeguridadService {

	public Seguridad(Session session) {
		super(session);
	}

}
