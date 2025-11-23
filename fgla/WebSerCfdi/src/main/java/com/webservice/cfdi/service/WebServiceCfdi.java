package com.webservice.cfdi.service;

import java.math.BigDecimal;

import com.webservice.cfdi.util.exception.CfdiException;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface WebServiceCfdi {

	<T> T consultarCfdi(String rfcEmisor, String rfcReceptor, BigDecimal totalFacturado, String uuidTimbrado)
			throws CfdiException;

}
