package com.sisecofi.proyectos.microservicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.util.consumer.HeaderUtil;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Service
@Profile("produccion")
public class CatalogoMicroservicioImpl extends CatalogoMicroservicio {

	private final HttpServletRequest request;

	public CatalogoMicroservicioImpl(RestTemplate restTemplate, @Value("${url.catalogos}") String url,
			HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
        return HeaderUtil.generarHeaders(request, obj);
    }

}
