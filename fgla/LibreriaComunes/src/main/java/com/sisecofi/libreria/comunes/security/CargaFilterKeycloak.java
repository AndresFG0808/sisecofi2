package com.sisecofi.libreria.comunes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * Configuración de Spring Security para Keycloak
 * Solo se activa con el perfil "keycloak"
 * 
 * @author ayuso2104@gmail.com
 */
@Configuration
@Profile("keycloak")
@RequiredArgsConstructor
public class CargaFilterKeycloak {

    private final CustomFilterKeycloak customFilterKeycloak;

    @Bean
    public SecurityFilterChain filterChainKeycloak(HttpSecurity http) throws Exception {
        http
            // Configurar manejo de excepciones
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(new AuthError())
            )
            // Agregar nuestro filtro personalizado de Keycloak
            .addFilterBefore(customFilterKeycloak, BasicAuthenticationFilter.class)
            // Deshabilitar CSRF para APIs REST
            .csrf(csrf -> csrf.disable())
            // Configurar headers de seguridad
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; object-src 'none'")
                )
            )
            // Configurar autorización de endpoints
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso a endpoints de Swagger/OpenAPI
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Todos los demás endpoints requieren autenticación
                .anyRequest().authenticated()
            );

        return http.build();
    }
}