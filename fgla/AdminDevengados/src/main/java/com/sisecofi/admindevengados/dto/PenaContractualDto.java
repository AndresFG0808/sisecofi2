package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PenaContractualDto {
	
	private Long idPenaContractual;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private String idDictamen;
	
	private Integer tipo;
	
	private Integer documentos;
	
	private String penaAplicable;
	
	private Long desglose;
	
	private String conceptoServicio;
	
	@Digits(integer = 20, fraction = 2)
	private BigDecimal monto;

}
