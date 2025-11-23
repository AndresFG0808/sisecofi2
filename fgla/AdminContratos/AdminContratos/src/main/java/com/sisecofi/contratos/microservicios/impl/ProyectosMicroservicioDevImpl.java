package com.sisecofi.contratos.microservicios.impl;

import com.sisecofi.contratos.microservicios.ProyectosMicroservicio;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Profile("dev")
public class ProyectosMicroservicioDevImpl extends ProyectosMicroservicio {

	private final HttpServletRequest request;

	public ProyectosMicroservicioDevImpl(RestTemplate restTemplate,
										 @Value("${url.proyecto}") String url,
										 HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
		return new HttpEntity<>(requestHeaders(request, obj));
	}
}
