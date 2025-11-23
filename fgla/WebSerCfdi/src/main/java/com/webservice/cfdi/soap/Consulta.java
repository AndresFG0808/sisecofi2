
package com.webservice.cfdi.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tem:Consulta")
public class Consulta {

	@XmlElement(name = "tem:expresionImpresa")
	protected ExpresionImpresa expresionImpresa;

	public Consulta() {
		super();
	}

	public Consulta(ExpresionImpresa expresionImpresa) {
		super();
		this.expresionImpresa = expresionImpresa;
	}

	public ExpresionImpresa getExpresionImpresa() {
		return expresionImpresa;
	}

	public void setExpresionImpresa(ExpresionImpresa expresionImpresa) {
		this.expresionImpresa = expresionImpresa;
	}

}
