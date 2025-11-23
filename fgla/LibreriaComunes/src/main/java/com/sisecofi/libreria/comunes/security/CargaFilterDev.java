package com.sisecofi.libreria.comunes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.sisecofi.libreria.comunes.dto.UsernameToken;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class CargaFilterDev {

	private final UsernameToken usernameToken;

	public CargaFilterDev(UsernameToken usernameToken) {
		super();
		this.usernameToken = usernameToken;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(new AuthError()))
	        .addFilterAfter(new CustomFilterDev(usernameToken), BasicAuthenticationFilter.class)
	        .csrf(csrf -> csrf.disable())
	        .headers(headers -> headers
	            .contentSecurityPolicy(csp -> csp
	                .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; object-src 'none'")
	            )
	        );

	    return http.build();
	}

}
