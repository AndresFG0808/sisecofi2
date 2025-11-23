package com.webservice.cfdi.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.webservice.cfdi.service.SoapCfdiPeticion;

@Component
@Scope("prototype")
public class SoapCfdiPeticionImpl extends SoapCfdiPeticion {

	private static final String URN = "http://tempuri.org/";

	@Value("${cfdi.url.webservice}")
	private String urlIncidente;

	@Override
	public String getUrn() {
		return URN;
	}

	@Override
	public HttpURLConnection getUrl() throws Exception {
		URL url = new URL(urlIncidente);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		conn.setRequestProperty("SOAPAction", "http://tempuri.org/IConsultaCFDIService/Consulta");
		conn.setRequestMethod("POST");
		return conn;
	}

	@Override
	public HttpsURLConnection getUrlHttps() throws Exception {
		URL url = new URL(urlIncidente);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		conn.setRequestProperty("SOAPAction", "http://tempuri.org/IConsultaCFDIService/Consulta");
		conn.setRequestMethod("POST");
		return conn;
	}

}
