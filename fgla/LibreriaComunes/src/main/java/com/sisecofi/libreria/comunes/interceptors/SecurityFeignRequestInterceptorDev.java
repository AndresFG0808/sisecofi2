package com.sisecofi.libreria.comunes.interceptors;

import org.springframework.context.annotation.Profile;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
@Profile("dev")
public class SecurityFeignRequestInterceptorDev implements RequestInterceptor {

	private static final String AUTHENTICATION_HEADER = "Authorization";
	private static final String AUTHENTICATION_MICROSERVICIO = "consumo-interno";

	private final TokenUtil tokenUtil;

	public SecurityFeignRequestInterceptorDev(TokenUtil tokenUtil) {
		super();
		this.tokenUtil = tokenUtil;
	}

	@Override
	public void apply(RequestTemplate template) {
		propagateAuthorizationHeader(template);
	}

	private void propagateAuthorizationHeader(RequestTemplate template) {
		try {
			String token = tokenUtil.generarToken();
			log.info("Generando token solo para local: {}", token);
			template.header(AUTHENTICATION_HEADER, token);
			template.header(AUTHENTICATION_MICROSERVICIO, "true");
		} catch (IllegalStateException e) {
	        log.error("No hay contexto HTTP disponible para obtener el RequestAttributes");
	    } catch (IllegalArgumentException e) {
	        log.error("Header de autorización no está presente en la solicitud");
	    } catch (Exception e) {
	        log.error("Error inesperado al setear el token");
	    }
	}
}