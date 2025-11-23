package com.sisecofi.proyectos.microservicio.cierre;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

public abstract class BaseRestMicroservicio {

    private final RestTemplate restTemplate;
    private final String url;

    protected BaseRestMicroservicio(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    protected abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj);

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected List<Archivo> realizarSolicitud(String endpoint, Long idProyecto) {
        HttpEntity<Long> request = new HttpEntity<>(idProyecto);
        HttpEntity<Object> obj = generarHeaders(Optional.of(request));
        ResponseEntity<List> responseEntity = restTemplate.exchange(
                url + endpoint + idProyecto,
                HttpMethod.GET, obj, List.class);
        return responseEntity.getBody();
    }
    
    protected Boolean validarCancelacionProyecto(String endpoint, Long idProyecto) {
        HttpEntity<Long> request = new HttpEntity<>(idProyecto);
        HttpEntity<Object> obj = generarHeaders(Optional.of(request));
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                url + endpoint + idProyecto,
                HttpMethod.GET, obj, Boolean.class);
        return responseEntity.getBody();
    }
    

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected String getUrl() {
        return url;
    }
}

