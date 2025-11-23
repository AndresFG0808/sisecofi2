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

import jakarta.servlet.http.HttpServletRequest;

@Service
@Profile("produccion")
public class ProyectoMicroservicioImpl extends ProyectoMicroservicio {

	private final HttpServletRequest request;

	public ProyectoMicroservicioImpl(RestTemplate restTemplate, @Value("${url.proyectos}") String url,
			HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
		requestHeaders.add("Authorization", request.getHeader("Authorization"));
		if (obj.isPresent()) {
			HttpEntity<String> existingEntity = obj.get();
			requestHeaders.putAll(existingEntity.getHeaders());
		}
		return new HttpEntity<>(obj, requestHeaders);
	}

}
