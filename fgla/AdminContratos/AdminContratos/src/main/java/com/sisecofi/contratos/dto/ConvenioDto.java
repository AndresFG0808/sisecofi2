package com.sisecofi.contratos.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class ConvenioDto {
	private BigDecimal subtotal;
	private BigDecimal montoMaximoSinImpuestos;
	private BigDecimal montoMaximoConImpuestos;
	private BigDecimal montoPesos;
	private String numeroContrato;
	private CatIva catIva;
	private LocalDateTime fechaFinVigenciaServicios;
	private LocalDateTime fechaFinVigenciaContrato;
	private BigDecimal impuestos;
	private BigDecimal ivaCantidad;
	private BigDecimal ieps;
	private BigDecimal incremento;
	private CatTipoMoneda catTipoMoneda;
	private String numeroUltimoConvenio;
}
