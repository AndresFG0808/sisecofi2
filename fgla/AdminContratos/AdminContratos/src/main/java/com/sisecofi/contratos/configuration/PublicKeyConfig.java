package com.sisecofi.contratos.configuration;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.sisecofi.contratos.util.exception.PkException;
import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;
import com.sisecofi.libreria.comunes.util.jtw.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
@Slf4j
public class PublicKeyConfig {

	private String publicKey;

	@Value("${jwt.keys}")
	private String keys;

	@Bean(value = "sat-public-key")
	public PublicKey generatePublicKeyFromJwk() {
		try {
			JWKSet j = JwtUtil.getJwksFromString(keys);
			log.info("Se genera JWK: {}", j.isEmpty());
			return JwtUtil.getPublicKeyFromJwk(j);
		} catch (Exception e) {
			throw new PkException(Error.ERROR_PK);
		}
	}

	public PublicKey generatePublicKey() {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec pubKeySpecX509EncodedKeySpec = new X509EncodedKeySpec(
					Base64.getDecoder().decode(publicKey));
			PublicKey pp = kf.generatePublic(pubKeySpecX509EncodedKeySpec);
			log.info("generando PK:{}", pp);
			return pp;
		} catch (Exception e) {
			throw new PkException(Error.ERROR_PK);
		}
	}

	private enum Error implements DefinitionMessage {

		ERROR_PK("001", "Error al generar PK");

		private String clave;
		private String message;

		Error(String clave, String message) {
			this.clave = clave;
			this.message = message;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public String getClave() {
			return clave;
		}

	}
}
