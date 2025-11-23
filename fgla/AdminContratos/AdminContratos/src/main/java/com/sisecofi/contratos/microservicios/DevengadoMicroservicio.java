package com.sisecofi.contratos.microservicios;

import com.sisecofi.contratos.service.MicroservicioAbstract;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public abstract class DevengadoMicroservicio extends MicroservicioAbstract {

    private final RestTemplate restTemplate;
    private final String url;
  

    protected DevengadoMicroservicio(RestTemplate restTemplate, String url) {
        super();
        this.restTemplate = restTemplate;
        this.url = url;
	
    }

    public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj);

    
    public <T extends Dictamen> List<T>  obtenerDictamenesContrato(Long idContrato) {
        HttpEntity<String> request = new HttpEntity<>("{\"estatus\": true}");
    	HttpEntity<Object> obj = generarHeaders(Optional.of(request));
		ParameterizedTypeReference<List<T>> typeRef = new ParameterizedTypeReference<>() {
		};
		ResponseEntity<List<T>> responseEntity = restTemplate.exchange(url + "/obtener-contrato-id/" + idContrato,
				HttpMethod.GET, obj, typeRef);
		return responseEntity.getBody();
	}
    

}