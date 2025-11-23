package com.sisecofi.admindevengados.microservicio;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sisecofi.libreria.comunes.dto.factura.WebServiceDto;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
public abstract class FacturaCFDIMicroservicio {

	private final RestTemplate restTemplate;
	private final String url;

	protected FacturaCFDIMicroservicio(RestTemplate restTemplate, String url) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public abstract HttpEntity<Object> generarHeaders(Optional<HttpEntity<WebServiceDto>> obj);
    
	public ConsultaCfdiResponse consultarCfdi(WebServiceDto webServiceDto) {
		HttpEntity<WebServiceDto> request = new HttpEntity<>(webServiceDto);
		HttpEntity<Object> obj = generarHeaders(Optional.of(request));
		ResponseEntity<ConsultaCfdiResponse> responseEntity = restTemplate.exchange(url + "/consultar-cfdi",
				HttpMethod.POST, obj, ConsultaCfdiResponse.class);
		return responseEntity.getBody();
	}
	
}
