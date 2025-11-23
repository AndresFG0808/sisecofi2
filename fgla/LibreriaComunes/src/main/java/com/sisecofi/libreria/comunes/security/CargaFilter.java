package com.sisecofi.libreria.comunes.security;

import java.security.PublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class CargaFilter {

	private String jwtSecret;
	private PublicKey key;
	private String jwkEndpoint;

	public CargaFilter(String jwtSecret, PublicKey key) {
		super();
		this.jwtSecret = jwtSecret;
		this.key = key;
	}

	public CargaFilter(String jwtSecret) {
		super();
		this.jwtSecret = jwtSecret;
	}

	/**
	 * Constructor para OAuth2 con JWK endpoint.
	 * @param jwtSecret Secret de respaldo (puede ser null si solo usas OAuth2)
	 * @param jwkEndpoint URL del JWK endpoint (ej: http://localhost:9000/oauth2/jwks)
	 */
	public CargaFilter(String jwtSecret, String jwkEndpoint) {
		super();
		this.jwtSecret = jwtSecret;
		this.jwkEndpoint = jwkEndpoint;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CustomFilter customFilter;
		
		// Determinar qué tipo de filtro crear según lo configurado
		if (jwkEndpoint != null && !jwkEndpoint.isEmpty()) {
			// OAuth2 con JWK endpoint
			customFilter = new CustomFilter(jwtSecret, jwkEndpoint);
		} else if (key != null) {
			// SAT con clave pública
			customFilter = new CustomFilter(jwtSecret, key);
		} else {
			// Sistema antiguo con solo secret
			customFilter = new CustomFilter(jwtSecret, (PublicKey) null);
		}
		
	    http
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(new AuthError()))
	        .addFilterBefore(customFilter, BasicAuthenticationFilter.class)
	        .csrf(csrf -> csrf.disable())
	        .headers(headers -> headers
	            .contentSecurityPolicy(csp -> csp
	                .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; object-src 'none'")
	            )
	        );

	    /*http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.requestMatchers("/catalogos/**")
		.hasAuthority("cn=SAT_SISECOFI_ADMIN_SIS_SEC").anyRequest().authenticated());*/

	    return http.build();
	}

}
