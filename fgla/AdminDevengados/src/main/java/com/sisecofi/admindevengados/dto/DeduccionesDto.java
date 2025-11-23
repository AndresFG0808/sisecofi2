package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeduccionesDto {
	
	private Long idDeduccion;
	
	private String dictamenId;
	
	private String tipoDeduccion;
	
	private String idTipoDeduccion;
	
	private String documento;
	
	private String deduccionAplicable;
	
	private String desglose;
	
	private String conceptoServicio;
	
	private BigDecimal monto;

}
