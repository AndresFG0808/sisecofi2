
package com.webservice.cfdi.soap;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "consultaResult")
public class ConsultaCfdiResponse {

	@XmlElement(name = "ConsultaResult")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "ConsultaResult")
	@JacksonXmlProperty(localName = "ConsultaResult")
	protected AcuseCfdi consultaResult;

	public ConsultaCfdiResponse() {
		super();
	}

	public AcuseCfdi getConsultaResult() {
		return consultaResult;
	}

	public void setConsultaResult(AcuseCfdi value) {
		this.consultaResult = value;
	}

	@Override
	public String toString() {
		return "ConsultaCfdiResponse [consultaResult=" + consultaResult + "]";
	}

}
