package com.sisecofi.contratos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.sisecofi.libreria.comunes.security.CargaFilter;

/**
 * Configuración de seguridad para validar tokens JWT.
 * Soporta tokens OAuth2 del authorization-server mediante JWK endpoint.
 * 
 * @author ayuso2104@gmail.com
 */

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@Profile("produccion")
public class FiltroToken extends CargaFilter {
	
	/**
	 * Constructor que configura el filtro para validar tokens OAuth2.
	 * 
	 * @param secret Secret JWT de respaldo (no usado si jwkEndpoint está configurado)
	 * @param jwkEndpoint URL del JWK endpoint del authorization-server
	 */
	public FiltroToken(
			@Value("${jwt.secret:ClaveSegura}") String secret,
			@Value("${jwt.jwk.endpoint:}") String jwkEndpoint) {
		super(secret, jwkEndpoint);
	}
	
}
