package com.sisecofi.proyectos.microservicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.interceptors.TokenUtil;
import com.sisecofi.libreria.comunes.util.consumer.HeaderUtil;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@Profile("dev")
public class CatalogoMicroservicioDevImpl extends CatalogoMicroservicio {

	private final TokenUtil tokenUtil;

	public CatalogoMicroservicioDevImpl(RestTemplate restTemplate, @Value("${url.catalogos}") String url,
			TokenUtil tokenUtil) {
		super(restTemplate, url);
		this.tokenUtil = tokenUtil;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
        return HeaderUtil.generarHeaders(tokenUtil, obj);
    }

}
