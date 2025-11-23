package com.ecommerce.oauth.security;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

/**
 * Customiza los tokens JWT para incluir roles y claims personalizados.
 * Necesario para que los microservices SISECOFI puedan validar roles.
 */
@Component
public class JwtTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        Object principal = context.getPrincipal().getPrincipal();
        
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            
            // Agregar roles al claim "roles" (para compatibilidad con SISECOFI)
            var roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            
            context.getClaims().claim("roles", roles);
            
            // También agregar al claim "authorities" (para compatibilidad)
            context.getClaims().claim("authorities", roles);
            
            // Agregar username
            context.getClaims().claim("user_name", userDetails.getUsername());
        }
    }
}
