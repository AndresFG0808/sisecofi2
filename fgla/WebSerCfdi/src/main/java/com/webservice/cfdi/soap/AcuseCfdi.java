
package com.webservice.cfdi.soap;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AcuseCfdi {

	@XmlElement(name = "CodigoEstatus")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "CodigoEstatus")
	@JacksonXmlProperty(localName = "CodigoEstatus")
	protected String codigoEstatus;

	@XmlElement(name = "EsCancelable")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "EsCancelable")
	@JacksonXmlProperty(localName = "EsCancelable")
	protected String esCancelable;

	@XmlElement(name = "Estado")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "Estado")
	@JacksonXmlProperty(localName = "Estado")
	protected String estado;

	@XmlElement(name = "EstatusCancelacion")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "EstatusCancelacion")
	@JacksonXmlProperty(localName = "EstatusCancelacion")
	protected String estatusCancelacion;

	@XmlElement(name = "ValidacionEFOS")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "ValidacionEFOS")
	@JacksonXmlProperty(localName = "ValidacionEFOS")
	protected String validacionEFOS;

	public String getCodigoEstatus() {
		return codigoEstatus;
	}

	public void setCodigoEstatus(String codigoEstatus) {
		this.codigoEstatus = codigoEstatus;
	}

	public String getEsCancelable() {
		return esCancelable;
	}

	public void setEsCancelable(String esCancelable) {
		this.esCancelable = esCancelable;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstatusCancelacion() {
		return estatusCancelacion;
	}

	public void setEstatusCancelacion(String estatusCancelacion) {
		this.estatusCancelacion = estatusCancelacion;
	}

	public String getValidacionEFOS() {
		return validacionEFOS;
	}

	public void setValidacionEFOS(String validacionEFOS) {
		this.validacionEFOS = validacionEFOS;
	}

	@Override
	public String toString() {
		return "AcuseCfdi [codigoEstatus=" + codigoEstatus + ", esCancelable=" + esCancelable + ", estado=" + estado
				+ ", estatusCancelacion=" + estatusCancelacion + ", validacionEFOS=" + validacionEFOS + "]";
	}

}
