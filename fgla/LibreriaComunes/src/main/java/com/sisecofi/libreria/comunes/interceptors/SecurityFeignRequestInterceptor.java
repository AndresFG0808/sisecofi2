package com.sisecofi.libreria.comunes.interceptors;

import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
@Profile("produccion")
public class SecurityFeignRequestInterceptor implements RequestInterceptor {

	private static final String AUTHENTICATION_HEADER = "Authorization";
	private static final String AUTHENTICATION_MICROSERVICIO = "consumo-interno";

	@Override
	public void apply(RequestTemplate template) {
		propagateAuthorizationHeader(template);
	}

	private void propagateAuthorizationHeader(RequestTemplate template) {
		try {
			if (template.headers().containsKey(AUTHENTICATION_HEADER)) {
				log.info("Token ya existente: {}", AUTHENTICATION_HEADER);
				template.header(AUTHENTICATION_MICROSERVICIO, "true");
			} else {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				log.info("Authorization propagada: {}", AUTHENTICATION_HEADER);
				template.header(AUTHENTICATION_HEADER, request.getHeader(AUTHENTICATION_HEADER));
				template.header(AUTHENTICATION_MICROSERVICIO, "true");
				
			}
		} catch (IllegalStateException e) {
	        log.error("No hay contexto HTTP disponible para obtener el RequestAttributes");
	    } catch (IllegalArgumentException e) {
	        log.error("Header de autorización no está presente en la solicitud");
	    } catch (Exception e) {
	        log.error("Error inesperado al setear el token");
	    }
	}

}
