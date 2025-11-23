package com.sisecofi.proyectos.microservicio.cierre.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.proyectos.microservicio.cierre.ContratoRestMicroservicio;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Service
@Profile("produccion")
public class ContratoRestMicroservicioImpl extends ContratoRestMicroservicio {

	private final BaseRestMicroservicioImpl baseRestUtil;

	public ContratoRestMicroservicioImpl(RestTemplate restTemplate, @Value("${url.contratos}") String url,
			HttpServletRequest request) {
		super(restTemplate, url);
		this.baseRestUtil = new BaseRestMicroservicioImpl(request) {
		};
	}

	@Override
	public HttpEntity<Object> generarHeaders(Optional<HttpEntity<Long>> obj) {
		return baseRestUtil.generarHeaders(obj);
	}
}
