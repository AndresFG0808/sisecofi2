package com.webservice.cfdi.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.webservice.cfdi.util.enums.ErroresCdfiEnum;
import com.webservice.cfdi.util.exception.CfdiException;

public abstract class SoapCfdiPeticion {

	protected static final Logger LOG = LoggerFactory.getLogger(SoapCfdiPeticion.class);

	private static final String SOAPENV = "soapenv";

	protected String pathCertificado;
	protected String password;
	protected long espera;

	public abstract String getUrn();

	public abstract HttpURLConnection getUrl() throws Exception; // NOSONAR

	public abstract HttpsURLConnection getUrlHttps() throws Exception; // NOSONAR

	private <T> SOAPMessage regresarPeticion(T object, String msj) throws CfdiException {
		try {
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			SOAPMessage soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();
			envelope.addNamespaceDeclaration("tem", getUrn());
			envelope.removeNamespaceDeclaration("xmlns:SOAP-ENV");
			SOAPHeader header = envelope.getHeader();
			header.setPrefix(SOAPENV);
			SOAPBody soapBody = envelope.getBody();
			soapBody.setPrefix(SOAPENV);
			JAXBContext jc = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(object, soapBody);
			Node e = soapBody.getLastChild();
			CDATASection cdataSection = soapMessage.getSOAPPart().createCDATASection(msj);
			e.getLastChild().appendChild(cdataSection);
			envelope.setPrefix(SOAPENV);
			soapMessage.saveChanges();
			return soapMessage;
		} catch (Exception e) {
			throw new CfdiException(ErroresCdfiEnum.ERROR_DIRECTORIO_ACTIVO, e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T, M> M peticion(T object, M response, String msj) throws CfdiException {
	    try {
	        SOAPMessage soapMessage = this.regresarPeticion(object, msj);
	        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
	            soapMessage.writeTo(bout);
	            String msg = bout.toString(StandardCharsets.UTF_8);
	            LOG.info("Peticion {} ", msg);
	        }

	        HttpsURLConnection conn = getUrlHttps();

	        try (OutputStream os = conn.getOutputStream()) {
	            soapMessage.writeTo(os);
	        }

	        StringBuilder out = new StringBuilder();
	        try (Reader reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) {
	            int ch;
	            while ((ch = reader.read()) != -1) {
	                out.append((char) ch);
	            }
	        }

	        XmlMapper xmlMapper = new XmlMapper();
	        String uno = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body>";
	        String dos = "</s:Body></s:Envelope>";
	        String re = out.toString().replace(uno, "").replace(dos, "");
	        LOG.debug("\n\nResponse: {}", re);

	        return (M) xmlMapper.readValue(re, response.getClass());

	    } catch (Exception e) {
	        LOG.error("Error durante la petición SOAP:");
	        throw new CfdiException(ErroresCdfiEnum.ERROR_DIRECTORIO_ACTIVO, e);
	    }
	}


}
