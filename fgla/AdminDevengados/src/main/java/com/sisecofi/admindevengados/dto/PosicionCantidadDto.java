package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Validated
public class PosicionCantidadDto {	
	private List<DictaminadoDto> listaDictaminados;
	private String idEstimacion;
	private BigDecimal monto;

}
