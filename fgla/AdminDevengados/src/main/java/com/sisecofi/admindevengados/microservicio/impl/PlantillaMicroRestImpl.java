package com.sisecofi.admindevengados.microservicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.admindevengados.microservicio.PlantillaMicroRest;
import com.sisecofi.libreria.comunes.util.consumer.HeaderUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Profile("produccion")
public class PlantillaMicroRestImpl extends PlantillaMicroRest {

	private final HttpServletRequest request;

	public PlantillaMicroRestImpl(RestTemplate restTemplate, @Value("${url.plantilla.documental}") String url,
			HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
        return HeaderUtil.generarHeaders(request, obj);
    }

}
