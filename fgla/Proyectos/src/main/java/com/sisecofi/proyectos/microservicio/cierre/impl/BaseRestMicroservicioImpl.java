package com.sisecofi.proyectos.microservicio.cierre.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseRestMicroservicioImpl {

    private final HttpServletRequest request;

    protected BaseRestMicroservicioImpl(HttpServletRequest request) {
        this.request = request;
    }

    public HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", request.getHeader("Authorization"));
        obj.ifPresent(existingEntity -> requestHeaders.putAll(existingEntity.getHeaders()));
        return new HttpEntity<>(obj.map(HttpEntity::getBody).orElse(null), requestHeaders);
    }
}

