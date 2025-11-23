package com.sisecofi.contratos.microservicios;

import com.sisecofi.contratos.service.MicroservicioAbstract;

import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public abstract class ProyectosMicroservicio extends MicroservicioAbstract {

	private final RestTemplate restTemplate;
	private final String url;

	protected ProyectosMicroservicio(RestTemplate restTemplate, String url) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj);

	public List<ProyectoSimpleDto> obtenerProyectos() {
		ResponseEntity<List<ProyectoSimpleDto>> responseEntity = restTemplate.exchange(url + "proyectos", HttpMethod.GET,
				generarHeaders(Optional.empty()), new ParameterizedTypeReference<List<ProyectoSimpleDto>>() {
				});
		return responseEntity.getBody();
	}
	
	public List<ProyectoSimpleDto> obtenerProyectosCompletos() {
		ResponseEntity<List<ProyectoSimpleDto>> responseEntity = restTemplate.exchange(url + "proyectos-completos", HttpMethod.GET,
				generarHeaders(Optional.empty()), new ParameterizedTypeReference<List<ProyectoSimpleDto>>() {
				});
		return responseEntity.getBody();
	}

	public ProyectoResponse obtenerProyecto(Long idProyecto) {
		ResponseEntity<ProyectoResponse> responseEntity = restTemplate.exchange(url + "proyecto/"+ idProyecto, HttpMethod.GET,
				generarHeaders(Optional.empty()), new ParameterizedTypeReference<ProyectoResponse>() {
				});
		return responseEntity.getBody();
	}

}
