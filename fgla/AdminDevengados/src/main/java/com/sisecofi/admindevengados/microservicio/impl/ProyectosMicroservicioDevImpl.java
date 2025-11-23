package com.sisecofi.admindevengados.microservicio.impl;

import com.sisecofi.admindevengados.microservicio.ProyectoMicroservicio;
import com.sisecofi.libreria.comunes.interceptors.TokenUtil;
import com.sisecofi.libreria.comunes.util.consumer.HeaderUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Profile("dev")
public class ProyectosMicroservicioDevImpl extends ProyectoMicroservicio {
    private final TokenUtil tokenUtil;

    public ProyectosMicroservicioDevImpl(RestTemplate restTemplate, @Value("${url.proyecto}") String url,
                                         TokenUtil tokenUtil) {
        super(restTemplate, url);
        this.tokenUtil = tokenUtil;
    }

    public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
        return HeaderUtil.generarHeaders(tokenUtil, obj);
    }
}
