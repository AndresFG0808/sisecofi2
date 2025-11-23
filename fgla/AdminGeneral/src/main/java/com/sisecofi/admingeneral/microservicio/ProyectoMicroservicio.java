package com.sisecofi.admingeneral.microservicio;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class ProyectoMicroservicio {

	private final RestTemplate restTemplate;
	private final String url;

	protected ProyectoMicroservicio(RestTemplate restTemplate, String url) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj);

	public List<Integer> plantillasOcupadas() {
		ResponseEntity<List<Integer>> responseEntity = restTemplate.exchange(url + "plantillasOcupadas", HttpMethod.GET,
				generarHeaders(Optional.empty()), new ParameterizedTypeReference<List<Integer>>() {
				});
		return responseEntity.getBody();
	}

}
