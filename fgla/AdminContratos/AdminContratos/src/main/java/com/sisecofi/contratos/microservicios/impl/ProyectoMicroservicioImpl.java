package com.sisecofi.contratos.microservicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.contratos.microservicios.ProyectosMicroservicio;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Profile("produccion")
public class ProyectoMicroservicioImpl extends ProyectosMicroservicio {

	private final HttpServletRequest request;

	public ProyectoMicroservicioImpl(RestTemplate restTemplate,
									 @Value("${url.proyecto}")  String url,
									 HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
		return new HttpEntity<>(obj, requestHeaders(request, obj));
	}

}
