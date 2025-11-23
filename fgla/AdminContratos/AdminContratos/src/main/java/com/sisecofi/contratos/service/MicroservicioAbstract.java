package com.sisecofi.contratos.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Optional;

public abstract class MicroservicioAbstract {

    protected HttpHeaders requestHeaders(HttpServletRequest request, Optional<HttpEntity<String>> obj) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", request.getHeader("Authorization"));
        if (obj.isPresent()) {
            HttpEntity<String> existingEntity = obj.get();
            requestHeaders.putAll(existingEntity.getHeaders());
        }
        return requestHeaders;
    }
}
