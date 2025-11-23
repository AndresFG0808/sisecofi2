package com.webservice.cfdi.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.cfdi.service.SoapCfdiPeticion;
import com.webservice.cfdi.service.WebServiceCfdi;
import com.webservice.cfdi.soap.Consulta;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;
import com.webservice.cfdi.soap.ExpresionImpresa;
import com.webservice.cfdi.util.exception.CfdiException;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
public class WebServiceCfdiImpl implements WebServiceCfdi {

	@Autowired
	private SoapCfdiPeticion soapPeticion;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T consultarCfdi(String rfcEmisor, String rfcReceptor, BigDecimal totalFacturado, String uuidTimbrado)
			throws CfdiException {
		StringBuilder s = new StringBuilder();
		s.append("?re=");
		s.append(rfcEmisor);
		s.append("&rr=");
		s.append(rfcReceptor);
		s.append("&tt=");
		s.append(totalFacturado);
		s.append("&id=");
		s.append(uuidTimbrado);
		return (T) soapPeticion.peticion(new Consulta(new ExpresionImpresa()), new ConsultaCfdiResponse(),
				s.toString());
	}

}
