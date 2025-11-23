package com.sisecofi.libreria.comunes.util.jtw;

import java.security.PublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);
	private String jwtSecret = "ASDASDOIANFHAOSJDA";
	private long tokenValidity = 1000;

	private JwtUtil() {
	}

	public Claims getClaims(final String token) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
	        LOG.warn("El token ha expirado:");
	    } catch (io.jsonwebtoken.SignatureException e) {
	        LOG.error("Firma inválida en el token:");
	    } catch (io.jsonwebtoken.MalformedJwtException e) {
	        LOG.error("Token mal formado:");
	    } catch (IllegalArgumentException e) {
	        LOG.error("Token nulo o vacío:");
	    } catch (Exception e) {
	        LOG.error("Error inesperado al procesar el token:");
	    }
		return Jwts.claims();
	}

	public String generateToken(String id) {
		Claims claims = Jwts.claims().setSubject(id);
		claims.put("rfc_largo", "NOPC840119DK8");
		claims.put("rfc", "NOPC841J");
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + tokenValidity;
		Date exp = new Date(expMillis);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public static Claims validateToken(final String token, final String jwtSecret) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
		} catch (MalformedJwtException ex) {
			throw new JwtException("Invalid JWT token" + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			throw new JwtException("Expired JWT token" + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			throw new JwtException("Unsupported JWT token" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new JwtException("JWT claims string is empty." + ex.getMessage());
		}
	}

	public static Claims validateTokenWithPublicKey(final String token, final PublicKey key) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		} catch (MalformedJwtException ex) {
			throw new JwtException("Invalid JWT token" + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			throw new JwtException("Expired JWT token" + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			throw new JwtException("Unsupported JWT token" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new JwtException("JWT claims string is empty." + ex.getMessage());
		}
	}

	public static JWKSet getJwksFromString(String cadena) {
		try {
			return JWKSet.parse(cadena);
		} catch (ParseException e) {
			throw new JwtException("getJwksFromString" + e.getMessage());
		}
	}

	public static PublicKey getPublicKeyFromJwk(JWKSet jwkSet) {
		try {
			Iterator<JWK> iterator = jwkSet.getKeys().iterator();
			RSAKey rsaKey = (RSAKey) iterator.next(); // Obtener la primera clave RSA
			return rsaKey.toRSAPublicKey();
		} catch (JOSEException e) {
			throw new JwtException("getPublicKeyFromJwk" + e.getMessage());
		}
	}

	/**
	 * Valida un token JWT RS256 usando la clave pública obtenida del JWK endpoint.
	 * Este método es compatible con tokens generados por authorization-server (OAuth2).
	 * 
	 * @param token El token JWT a validar
	 * @param jwkEndpointUrl URL del endpoint JWK (ej: http://localhost:9000/oauth2/jwks)
	 * @return Claims del token validado
	 * @throws JwtException si el token es inválido
	 */
	public static Claims validateTokenFromAuthServer(final String token, final String jwkEndpointUrl) {
		try {
			// Obtener el JWKSet desde el endpoint
			java.net.URL url = new java.net.URL(jwkEndpointUrl);
			JWKSet jwkSet = JWKSet.load(url);
			
			// Obtener la clave pública
			PublicKey publicKey = getPublicKeyFromJwk(jwkSet);
			
			// Validar el token con la clave pública
			return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
		} catch (MalformedJwtException ex) {
			throw new JwtException("Invalid JWT token: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			throw new JwtException("Expired JWT token: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			throw new JwtException("Unsupported JWT token: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new JwtException("JWT claims string is empty: " + ex.getMessage());
		} catch (java.io.IOException ex) {
			throw new JwtException("Error loading JWK from endpoint: " + ex.getMessage());
		} catch (Exception ex) {
			throw new JwtException("Error validating token: " + ex.getMessage());
		}
	}
}
