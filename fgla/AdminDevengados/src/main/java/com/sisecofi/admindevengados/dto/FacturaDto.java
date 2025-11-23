package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Valid
public class FacturaDto {
	
	private Long idFactura;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private String archivoCargar;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private String pdf;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private String folio;
	
	private String emisorRFC;
	
	private String emisorNombre;
	
	private String comprobanteFiscal;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private LocalDateTime fecha;
	
	private Long estatus;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private String moneda;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private String tasaoCuota;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal subTotal;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Digits(integer = 20, fraction = 2)
	private BigDecimal ieps;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Digits(integer = 20, fraction = 2)
	private BigDecimal iva;
	
	@Digits(integer = 20, fraction = 2)
	private BigDecimal otrosImpuestos;
	
	@Digits(integer = 20, fraction = 2)
	private BigDecimal total;
	
	@Digits(integer = 20, fraction = 2)
	private BigDecimal totalPesos;
	
	private DictamenId dictamenId;
	
	private Long idContrato;
	
	private Long idProveedor;
	
	private String comentarios;
	
	private List<ConceptoDto> conceptos;

}
