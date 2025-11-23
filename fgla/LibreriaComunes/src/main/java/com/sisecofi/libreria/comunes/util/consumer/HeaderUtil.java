package com.sisecofi.libreria.comunes.util.consumer;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.sisecofi.libreria.comunes.interceptors.TokenUtil;

import jakarta.servlet.http.HttpServletRequest;

public class HeaderUtil {
	
	private static final String AUTORIZACION= "Authorization";

	private HeaderUtil() {
	}

	public static HttpEntity<Object> generarHeaders(HttpServletRequest request, Optional<HttpEntity<String>> obj) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add(AUTORIZACION, request.getHeader(AUTORIZACION));
		if (obj.isPresent()) {
			HttpEntity<String> existingEntity = obj.get();
			requestHeaders.putAll(existingEntity.getHeaders());
		}
		return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders);
	}

	public static HttpEntity<Object> generarHeaders(TokenUtil tokenUtil, Optional<HttpEntity<String>> obj) {
		String token = tokenUtil.generarToken();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add(AUTORIZACION, "Bearer " + token);
		if (obj.isPresent()) {
			HttpEntity<String> existingEntity = obj.get();
			requestHeaders.putAll(existingEntity.getHeaders());
		}
		return new HttpEntity<>(obj.isPresent() ? obj.get().getBody() : null, requestHeaders);
	}

}
