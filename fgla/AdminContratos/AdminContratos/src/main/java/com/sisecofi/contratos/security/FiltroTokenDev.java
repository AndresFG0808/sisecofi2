package com.sisecofi.contratos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.sisecofi.libreria.comunes.dto.UsernameToken;
import com.sisecofi.libreria.comunes.security.CargaFilterDev;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@Profile("dev")
public class FiltroTokenDev extends CargaFilterDev {

	public FiltroTokenDev(@Autowired UsernameToken usernameToken) {
		super(usernameToken);
	}

}
