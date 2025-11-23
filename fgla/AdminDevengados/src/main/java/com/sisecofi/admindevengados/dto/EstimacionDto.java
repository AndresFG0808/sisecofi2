package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstimacionDto {
	
	private String idEstimacion;

	private LocalDateTime fechaInicio;

	private LocalDateTime fechaTermino;

	private String periodoControl;

	private BigDecimal montoEstimado; // Monto estimado total

	private BigDecimal montoEstimadoPesos;

}
