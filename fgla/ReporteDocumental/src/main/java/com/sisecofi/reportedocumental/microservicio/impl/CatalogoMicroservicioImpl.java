package com.sisecofi.reportedocumental.microservicio.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.reportedocumental.microservicio.CatalogoMicroservicio;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Service
@Profile("produccion")
public class CatalogoMicroservicioImpl extends CatalogoMicroservicio {

	private final HttpServletRequest request;

	public CatalogoMicroservicioImpl(RestTemplate restTemplate, @Value("${url.catalogos}") String url,
			HttpServletRequest request) {
		super(restTemplate, url);
		this.request = request;
	}

	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<String>> obj) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", request.getHeader("Authorization"));
		if (obj.isPresent()) {
			HttpEntity<String> existingEntity = obj.get();
			requestHeaders.putAll(existingEntity.getHeaders());
		}
		return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders);
	}

}
