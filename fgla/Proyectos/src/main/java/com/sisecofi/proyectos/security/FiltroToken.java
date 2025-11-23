package com.sisecofi.proyectos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.sisecofi.libreria.comunes.security.CargaFilter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@Profile("produccion")
public class FiltroToken extends CargaFilter {

	public FiltroToken(@Value("${jwt.secret}") String secret, @Value("${jwt.jwk.endpoint:}") String jwkEndpoint) {
		super(secret, jwkEndpoint);
	}
	
}
