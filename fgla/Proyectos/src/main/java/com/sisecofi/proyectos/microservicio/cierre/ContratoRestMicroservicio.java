package com.sisecofi.proyectos.microservicio.cierre;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

public class ContratoRestMicroservicio extends BaseRestMicroservicio {

    public ContratoRestMicroservicio(RestTemplate restTemplate, String url) {
        super(restTemplate, url);
    }

    @Override
    protected HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj) {
        return new HttpEntity<>(new HttpHeaders());
    }

    public List<Archivo> obtenerArchivosSeccion(Long idProyecto) {
        return realizarSolicitud("/admin-contratos-internos/obtener-archivos-contratos/", idProyecto);
    }
}

