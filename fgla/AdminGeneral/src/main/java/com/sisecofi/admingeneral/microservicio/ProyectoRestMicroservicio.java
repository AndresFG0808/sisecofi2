package com.sisecofi.admingeneral.microservicio;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

public abstract class ProyectoRestMicroservicio {

	private final RestTemplate restTemplate;
	private final String url;

	protected ProyectoRestMicroservicio(RestTemplate restTemplate, String url) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Archivo> obtenerArchivosSeccion(Long idProyecto) {	    
		ResponseEntity<List> responseEntity = restTemplate.exchange(
	            url + "/obtener-archivos-seccion/"+idProyecto,
	            HttpMethod.GET, generarHeaders(Optional.empty()), List.class);	    
	    return responseEntity.getBody();
	}
}
