package com.sisecofi.libreria.comunes.dto.factura;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebServiceDto {
	
	private String rfcEmisor;	
	private String rfcReceptor;
	private BigDecimal totalFacturado;
	private String uuidTimbrado;

}
