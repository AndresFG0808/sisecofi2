package com.sisecofi.libreria.comunes.security;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sisecofi.libreria.comunes.dto.UsernameToken;
import com.sisecofi.libreria.comunes.util.jtw.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public class CustomFilter extends OncePerRequestFilter {

	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";
	private PublicKey key;
	private String secret;
	private String jwkEndpoint; // URL del JWK endpoint para tokens OAuth2

	public CustomFilter(String secret, PublicKey key) {
		super();
		this.key = key;
		this.secret = secret;
	}

	public CustomFilter(String secret, String jwkEndpoint) {
		super();
		this.secret = secret;
		this.jwkEndpoint = jwkEndpoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest request =  req;
		HttpServletResponse response = res;
		try {
			if (request.getServletPath().startsWith("/swagger-ui/")
					|| request.getServletPath().startsWith("/v3/api-docs")) {
				log.debug("Servicio de TEST");
			} else {
				if (existeJWTToken(request)) {
					Claims claims = validateToken(request);
					
					// Detectar tipo de token por sus claims
					if (claims.get("scope") != null || claims.get("sub") != null) {
						// Token OAuth2 del authorization-server
						log.debug("Login con OAuth2 authorization-server");
						setUpSpringAuthenticationOAuth2(claims);
					} else if (claims.get("roles") != null || claims.get("Roles") != null) {
						// Token SAT
						log.debug("Login con SAT");
						setUpSpringAuthenticationSat(claims);
					} else if (claims.get("authorities") != null) {
						// Token SYE (sistema antiguo)
						log.debug("Login con SYE");
						setUpSpringAuthenticationSye(claims);
					} else {
						log.error("Token sin claims reconocibles");
						SecurityContextHolder.clearContext();
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				} else {
					log.error("Usuario no autorizado");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							"full authentication is required to access this resource");
					SecurityContextHolder.clearContext();
				}
			}
			// Agregar la cabecera Content-Security-Policy
			response.addHeader("Content-Security-Policy", "default-src 'self';");
			// Agregar la cabecera X-XSS-Protection
			response.addHeader("X-XSS-Protection", "1; mode=block");
			// Agregar la cabecera X-Content-Type-Options
			response.addHeader("X-Content-Type-Options", "nosniff");
			filterChain.doFilter(request, response);
		}catch (RuntimeException e) {
	        log.error("Error en tiempo de ejecución en el filtro:");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.addHeader("error", e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
	    }
		catch (Exception e) {
			log.error("Error en filtro:");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.addHeader("error", e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		
		// Prioridad 1: Si hay JWK endpoint configurado, validar como OAuth2/RS256
		if (jwkEndpoint != null && !jwkEndpoint.isEmpty()) {
			log.debug("Validando token OAuth2 con JWK endpoint: {}", jwkEndpoint);
			return JwtUtil.validateTokenFromAuthServer(jwtToken, jwkEndpoint);
		}
		
		// Prioridad 2: Si hay clave pública (SAT), validar con ella
		if (key != null) {
			log.debug("Validando con certificado publico (SAT)");
			return JwtUtil.validateTokenWithPublicKey(jwtToken, key);
		}
		
		// Prioridad 3: Validar con secret local (método antiguo)
		log.debug("Validando con secret local (HS512)");
		return JwtUtil.validateToken(jwtToken, secret);
	}

	@SuppressWarnings("unchecked")
	private void setUpSpringAuthenticationSat(Claims claims) {
		log.debug("CLaims: {}", claims);
		List<String> authorities = (List<String>) claims.get("Roles");
		UsernameToken auth = new UsernameToken(claims.get("rfc"), null,
				authorities.stream().map(SimpleGrantedAuthority::new).toList(),
				claims.get("scope", List.class));
		SecurityContextHolder.getContext().setAuthentication(auth);
		UsernameToken pri = (UsernameToken) SecurityContextHolder.getContextHolderStrategy().getContext()
				.getAuthentication();
		log.debug("Usuario token: {}", pri.getPrincipal());
	}

	@SuppressWarnings("unchecked")
	private void setUpSpringAuthenticationSye(Claims claims) {
		log.debug("CLaims: {}", claims);
		List<String> authorities = (List<String>) claims.get("authorities");
		UsernameToken auth = new UsernameToken(claims.get("user_name"), null,
				authorities.stream().map(SimpleGrantedAuthority::new).toList(),
				claims.get("scope", List.class));
		SecurityContextHolder.getContext().setAuthentication(auth);
		UsernameToken pri = (UsernameToken) SecurityContextHolder.getContextHolderStrategy().getContext()
				.getAuthentication();
		log.debug("Usuario token: {}", pri.getPrincipal());
	}

	/**
	 * Configura la autenticación para tokens OAuth2 del authorization-server.
	 * Estos tokens tienen estructura: sub (username), scope (alcances), authorities (roles).
	 */
	@SuppressWarnings("unchecked")
	private void setUpSpringAuthenticationOAuth2(Claims claims) {
		log.debug("Claims OAuth2: {}", claims);
		
		// El username está en "sub" para OAuth2
		String username = claims.getSubject(); // "sub" claim
		
		// Los scopes pueden estar como lista o string separado por espacios
		Object scopeObj = claims.get("scope");
		List<String> scopes = null;
		if (scopeObj instanceof List) {
			scopes = (List<String>) scopeObj;
		} else if (scopeObj instanceof String) {
			scopes = List.of(((String) scopeObj).split(" "));
		}
		
		// Obtener roles del claim "roles" (incluye ROLE_* y cn=SAT_SISECOFI_*)
		List<String> roles = (List<String>) claims.get("roles");
		List<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>();
		
		// Agregar todos los roles del token
		if (roles != null && !roles.isEmpty()) {
			roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
			log.debug("Roles OAuth2 agregados: {}", roles);
		} else {
			// Fallback: agregar ROLE_USER por defecto
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			log.warn("Token OAuth2 sin roles, usando ROLE_USER por defecto");
		}
		
		// Si hay scopes, agregarlos también como authorities
		if (scopes != null) {
			scopes.forEach(scope -> authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope)));
		}
		
		UsernameToken auth = new UsernameToken(username, null, authorities, scopes);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		log.debug("Usuario OAuth2 autenticado: {} con {} authorities", username, authorities.size());
	}

	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	private boolean existeJWTToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		log.debug("Validando token: {}", authenticationHeader != null ? authenticationHeader.length() : null);
		return (authenticationHeader != null && authenticationHeader.startsWith(PREFIX));
	}

}