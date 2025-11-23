package com.sisecofi.admingeneral.microservicio.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.admingeneral.microservicio.ProyectoMicroservicio;
import com.sisecofi.libreria.comunes.interceptors.TokenUtil;

@Service
@Profile("dev")
public class ProyectoMicroservicioDevImpl extends ProyectoMicroservicio {

	private final TokenUtil tokenUtil;

	public ProyectoMicroservicioDevImpl(RestTemplate restTemplate, @Value("${url.proyectos}") String url,
			TokenUtil tokenUtil) {
		super(restTemplate, url);
		this.tokenUtil = tokenUtil;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
		String token = tokenUtil.generarToken();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", "Bearer " + token);
		return new HttpEntity<>(requestHeaders);
	}

}
