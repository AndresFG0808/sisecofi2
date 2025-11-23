package com.sisecofi.admindevengados.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ContratoDto {
	@EqualsAndHashCode.Include
	private long idContrato;
	private String nombreCorto;
	private boolean ejecucion;
}
