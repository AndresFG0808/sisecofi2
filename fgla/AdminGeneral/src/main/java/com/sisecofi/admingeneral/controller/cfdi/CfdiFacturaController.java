package com.sisecofi.admingeneral.controller.cfdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesCfdi;
import com.sisecofi.libreria.comunes.dto.factura.WebServiceDto;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.webservice.cfdi.service.WebServiceCfdi;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;
import com.webservice.cfdi.util.exception.CfdiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/" + Constantes.PATH_BASE)
public class CfdiFacturaController {

	@Autowired
	private WebServiceCfdi webServiceCfdi;

	@PostMapping(ConstantesCfdi.PATH_BASE_INTERNO + "/consultar-cfdi")
	@ConsumoInterno
	public ResponseEntity<ConsultaCfdiResponse> consultarCfdi(@RequestBody WebServiceDto webServiceDto) {
		try {
			ConsultaCfdiResponse response = webServiceCfdi.consultarCfdi(webServiceDto.getRfcEmisor(),
					webServiceDto.getRfcReceptor(), webServiceDto.getTotalFacturado(), webServiceDto.getUuidTimbrado());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (CfdiException e) {
			log.error("Error al consultar CFDI");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
