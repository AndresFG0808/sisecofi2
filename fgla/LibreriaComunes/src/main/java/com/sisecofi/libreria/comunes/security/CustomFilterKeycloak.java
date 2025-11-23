package com.sisecofi.libreria.comunes.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisecofi.libreria.comunes.dto.UsernameToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro personalizado para validar tokens JWT de Keycloak
 * Solo se activa con el perfil "keycloak"
 * 
 * @author ayuso2104@gmail.com
 */
@Slf4j
@Component
@Profile("keycloak")
public class CustomFilterKeycloak extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // Permitir acceso a endpoints de Swagger/OpenAPI
            if (request.getServletPath().startsWith("/swagger-ui/")
                    || request.getServletPath().startsWith("/v3/api-docs")) {
                log.debug("Permitiendo acceso a endpoint de documentación");
                filterChain.doFilter(request, response);
                return;
            }

            // Verificar si el token JWT existe
            if (existeJWTToken(request)) {
                // Validar token de Keycloak y extraer información
                String token = request.getHeader(HEADER).replace(PREFIX, "");
                
                // Por ahora, simulamos la validación del token
                // En una implementación completa, aquí validarías contra Keycloak
                if (validarTokenKeycloak(token)) {
                    setUpSpringAuthenticationKeycloak(token);
                } else {
                    log.error("Token Keycloak inválido");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    SecurityContextHolder.clearContext();
                    return;
                }
            } else {
                log.error("Usuario no autorizado - Token JWT no encontrado");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "full authentication is required to access this resource");
                SecurityContextHolder.clearContext();
                return;
            }

            // Agregar headers de seguridad
            response.addHeader("Content-Security-Policy", "default-src 'self';");
            response.addHeader("X-XSS-Protection", "1; mode=block");
            response.addHeader("X-Content-Type-Options", "nosniff");
            
            filterChain.doFilter(request, response);
            
        } catch (Exception e) {
            log.error("Error en CustomFilterKeycloak: ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("error", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * Verifica si existe un token JWT en el header Authorization
     */
    private boolean existeJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        log.debug("Validando token: {}", authenticationHeader != null ? "Token presente" : "Sin token");
        return (authenticationHeader != null && authenticationHeader.startsWith(PREFIX));
    }

    /**
     * Valida el token contra Keycloak
     * NOTA: Esta es una implementación simplificada para desarrollo local
     */
    private boolean validarTokenKeycloak(String token) {
        try {
            // TODO: Implementar validación real contra Keycloak
            // Por ahora, para desarrollo local, aceptamos cualquier token no vacío
            log.debug("Validando token Keycloak (modo desarrollo): {}", token.length() > 0 ? "Token válido" : "Token vacío");
            return token != null && !token.trim().isEmpty();
            
        } catch (Exception e) {
            log.error("Error validando token Keycloak: ", e);
            return false;
        }
    }

    /**
     * Configura la autenticación en Spring Security con información de Keycloak
     */
    private void setUpSpringAuthenticationKeycloak(String token) {
        try {
            // Por ahora, para desarrollo local, asignamos todos los roles SAT_SISECOFI
            // En producción, estos roles vendrían del token de Keycloak
            List<String> authorities = List.of(
                "cn=SAT_SISECOFI_ADMIN_SIS",
                "cn=SAT_SISECOFI_ADMIN_SIS_SEC", 
                "cn=SAT_SISECOFI_ADMIN_MAT_DOC",
                "cn=SAT_SISECOFI_APO_ACP",
                "cn=SAT_SISECOFI_APO_LID_PROY",
                "cn=SAT_SISECOFI_GES_TIT_AUT",
                "cn=SAT_SISECOFI_GES_DOC_CON",
                "cn=SAT_SISECOFI_USU_CONS",
                "cn=SAT_SISECOFI_LID_PRO",
                "cn=SAT_SISECOFI_ADMIN_CON",
                "cn=SAT_SISECOFI_PART_ADMIN_ESTIM",
                "cn=SAT_SISECOFI_PART_ADMIN_DICT",
                "cn=SAT_SISECOFI_PART_ADMIN_VERIF",
                "cn=SAT_SISECOFI_VERI_GEN",
                "cn=SAT_SISECOFI_VERI_ESP_CON",
                "cn=SAT_SISECOFI_TDS_PROY"
            );

            // Crear el token de usuario con todos los permisos para desarrollo
            UsernameToken auth = new UsernameToken(
                "keycloak-user", // Principal (usuario)
                null, // Credentials
                authorities.stream().map(SimpleGrantedAuthority::new).toList(), // Authorities
                List.of("read", "write", "update") // Scopes
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            log.debug("Usuario Keycloak autenticado: {}", auth.getPrincipal());
            
        } catch (Exception e) {
            log.error("Error configurando autenticación Keycloak: ", e);
            SecurityContextHolder.clearContext();
        }
    }
}