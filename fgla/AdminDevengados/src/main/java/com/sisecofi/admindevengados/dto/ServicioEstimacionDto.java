package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicioEstimacionDto {
	
	private Long idServicioEstimado;
	
	private BigDecimal cantidadServiciosEstimados;

}
