package com.sisecofi.contratos.microservicios;

import java.util.Optional;

import com.sisecofi.contratos.service.MicroservicioAbstract;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;

public abstract class PlantillaMicroRest extends MicroservicioAbstract {

	private static final String DELIMITADOR= "/";
	private static final String PATH_INFORMACION = DELIMITADOR+ "obtener-plantilla-verificacion";
	private static final String PATH_INFORMACION_REINTEGROS = DELIMITADOR+ "obtener-plantilla-reintegros";
	
	private final RestTemplate restTemplate;
	private final String url;

	protected PlantillaMicroRest(RestTemplate restTemplate, String url) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj);

	public CarpetaPlantillaModel obtenerArchivosVerificacion() {
		ResponseEntity<CarpetaPlantillaModel> responseEntity = restTemplate.exchange(url + PATH_INFORMACION, HttpMethod.GET,
				generarHeaders(Optional.empty()), CarpetaPlantillaModel.class);
		return responseEntity.getBody();
	}
	
	public CarpetaPlantillaModel obtenerArchivosReintegros() {
		ResponseEntity<CarpetaPlantillaModel> responseEntity = restTemplate.exchange(url + PATH_INFORMACION_REINTEGROS, HttpMethod.GET,
				generarHeaders(Optional.empty()), CarpetaPlantillaModel.class);
		return responseEntity.getBody();
	}
	

}
