package com.sisecofi.proyectos.microservicio.cierre.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.interceptors.TokenUtil;
import com.sisecofi.proyectos.microservicio.cierre.ContratoRestMicroservicio;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Profile("dev")
public class ContratoRestMicroservicioDevImpl extends ContratoRestMicroservicio {

	public final TokenUtil tokenUtil;
	private final BaseRestMicroservicioDevImpl baseRestUtil;

	public ContratoRestMicroservicioDevImpl(RestTemplate restTemplate, @Value("${url.contratos}") String url,
			TokenUtil tokenUtil, HttpServletRequest request) {
		super(restTemplate, url);
		this.tokenUtil = tokenUtil;
		this.baseRestUtil = new BaseRestMicroservicioDevImpl(request) {
		};
	}

	@Override
	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj) {
		return baseRestUtil.generarHeaders(obj);
	}
}
