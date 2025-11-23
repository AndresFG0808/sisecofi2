package com.sisecofi.libreria.comunes.dto.dictamen;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FacturaContratoDto {
	
	@JsonProperty("idDictamen")
	private Long idDictamen;
	
	@JsonProperty("idProveedor")
	private Long idProveedor;
	
	@JsonProperty("comprobanteFiscal")
	private String comprobanteFiscal;
	
	@JsonProperty("convenioColaboracion")
	private boolean convenioColaboracion;
	
	@JsonProperty("monto")
	private BigDecimal monto;
	
	@JsonProperty("montoPesos")
	private BigDecimal montoPesos;
	
	@JsonProperty("estatus")
	private String estatus;
	
	@JsonProperty("tipoCambioReferencial")
	private BigDecimal tipoCambioReferencial;

}
