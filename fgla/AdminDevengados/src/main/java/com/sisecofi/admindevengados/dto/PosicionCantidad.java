package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PosicionCantidad {

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private Integer posicion;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Digits(integer = 20, fraction = 6)
	private BigDecimal cantidad;
}
