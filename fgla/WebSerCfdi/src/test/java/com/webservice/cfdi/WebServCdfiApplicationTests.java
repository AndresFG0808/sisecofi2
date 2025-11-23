package com.webservice.cfdi;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.webservice.cfdi.service.WebServiceCfdi;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;
import com.webservice.cfdi.util.exception.CfdiException;

@SpringBootTest
class WebServCdfiApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(WebServCdfiApplicationTests.class);

	@Autowired
	private WebServiceCfdi cfdi;

	@Test
	void contextLoads() {
		try {
			LOG.info("Iniciando");
			ConsultaCfdiResponse consultaResponse = cfdi.consultarCfdi("AME050428RN2", "FAP211125MA2",
					new BigDecimal("1341757.47"), "C2962643-A572-48A8-85F0-3AE8D32B7ED5");
			LOG.info("Resultado de webservice: {}", consultaResponse);
		} catch (CfdiException e) {
			LOG.error("Error");
		}
	}

}
