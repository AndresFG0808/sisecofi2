package com.sisecofi.contratos.microservicios.impl;

import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Profile("produccion")
public class CatalogoMicroservicioImpl extends CatalogoMicroservicio {

    private final HttpServletRequest request;

    public CatalogoMicroservicioImpl(RestTemplate restTemplate,
                                     @Value("${url.catalogos}") String url,
                                     HttpServletRequest request) {
        super(restTemplate, url);
        this.request = request;
    }

    public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
        return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders(request, obj));
    }

}
