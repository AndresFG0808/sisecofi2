package com.sisecofi.admindevengados.microservicio;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import jakarta.servlet.http.HttpServletRequest;

public class FeignClientInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    public FeignClientInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void apply(RequestTemplate template) {
        // Obtener el token del encabezado de la solicitud
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            // Agregar el token a las solicitudes Feign
            template.header("Authorization", token);
        }
    }
}
