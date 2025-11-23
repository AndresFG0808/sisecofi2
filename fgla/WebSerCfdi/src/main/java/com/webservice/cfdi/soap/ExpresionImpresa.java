package com.webservice.cfdi.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpresionImpresa {

	protected String expresionImpresa;

	public ExpresionImpresa() {
		super();
	}

	public String getExpresionImpresa() {
		return expresionImpresa;
	}

	public void setExpresionImpresa(String expresionImpresa) {
		this.expresionImpresa = expresionImpresa;
	}
}
