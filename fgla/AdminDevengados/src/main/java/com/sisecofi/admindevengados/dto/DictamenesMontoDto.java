package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DictamenesMontoDto {
	
	private String idDictamen;
	
	private BigDecimal montoDictaminado;
	
	private String estatusDictamen;
	
	private String comprobanteFiscal;
	
	private String pendientePago;

}
