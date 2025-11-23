package com.sisecofi.admindevengados.microservicio;

import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoMetaDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public abstract class ProyectoMicroservicio {
    private final RestTemplate restTemplate;
    private final String url;

    protected ProyectoMicroservicio(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj);

    public List<ProyectoMetaDto> obtenerProyectos() {
        ResponseEntity<List<ProyectoMetaDto>> responseEntity = restTemplate.exchange(url + "proyectos", HttpMethod.GET,
                generarHeaders(Optional.empty()), new ParameterizedTypeReference<List<ProyectoMetaDto>>() {
                });
        return responseEntity.getBody();
    }

    public FichaTecnicaResponse obtenerFichaTecnica(Long idProyecto) {
   
            ResponseEntity<FichaTecnicaResponse> responseEntity = restTemplate.exchange(url + "ficha/"+ idProyecto, HttpMethod.GET,
                    generarHeaders(Optional.empty()), new ParameterizedTypeReference<FichaTecnicaResponse>() {
                    });
            return responseEntity.getBody();

 
    }

    public ProyectoResponse obtenerProyecto(Long idProyecto) {
        ResponseEntity<ProyectoResponse> responseEntity = restTemplate.exchange(url + "proyecto/"+ idProyecto, HttpMethod.GET,
                generarHeaders(Optional.empty()), new ParameterizedTypeReference<ProyectoResponse>() {
                });
        return responseEntity.getBody();
    }

    public ProyectoModel obtenerProyectoModel(Long idProyecto) {
        ResponseEntity<ProyectoModel> responseEntity = restTemplate.exchange(url + "proyecto-model/"+ idProyecto, HttpMethod.GET,
                generarHeaders(Optional.empty()), new ParameterizedTypeReference<ProyectoModel>() {
                });
        return responseEntity.getBody();
    }

}
