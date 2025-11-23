package com.sisecofi.contratos.microservicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.contratos.microservicios.PlantillaMicroRest;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Profile("dev")
public class PlantillaMicroRestDevImpl extends PlantillaMicroRest {

	private final HttpServletRequest request;

	public PlantillaMicroRestDevImpl(RestTemplate restTemplate,
									 @Value("${url.plantilla.documental}") String url,
									 HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
		return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders(request, obj));
	}

}
