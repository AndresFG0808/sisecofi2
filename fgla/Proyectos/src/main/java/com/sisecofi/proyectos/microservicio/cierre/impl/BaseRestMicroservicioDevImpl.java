package com.sisecofi.proyectos.microservicio.cierre.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseRestMicroservicioDevImpl {

    private final HttpServletRequest request;

    protected BaseRestMicroservicioDevImpl(HttpServletRequest request) {
        this.request = request;
    }

    public HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", request.getHeader("Authorization"));
        if (obj.isPresent()) {
            HttpEntity<Long> existingEntity = obj.get();
            requestHeaders.putAll(existingEntity.getHeaders());
        }
        return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders);
    }
}

