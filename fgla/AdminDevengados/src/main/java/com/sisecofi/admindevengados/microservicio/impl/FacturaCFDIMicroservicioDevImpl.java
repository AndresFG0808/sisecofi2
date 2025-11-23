package com.sisecofi.admindevengados.microservicio.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.dto.factura.WebServiceDto;
import com.sisecofi.libreria.comunes.interceptors.TokenUtil;


import com.sisecofi.admindevengados.microservicio.FacturaCFDIMicroservicio;

@Service
@Profile("dev")
public class FacturaCFDIMicroservicioDevImpl extends FacturaCFDIMicroservicio {

	private final TokenUtil tokenUtil;

	public FacturaCFDIMicroservicioDevImpl(RestTemplate restTemplate, @Value("${url.cfdi-factura}") String url,
			TokenUtil tokenUtil) {
		super(restTemplate, url);
		this.tokenUtil = tokenUtil;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<WebServiceDto>> obj) {
		String token = tokenUtil.generarToken();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", "Bearer " + token);
		if (obj.isPresent()) {
			HttpEntity<WebServiceDto> existingEntity = obj.get();
			requestHeaders.putAll(existingEntity.getHeaders());
		}
		return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders);
	}

}
