package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;

import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictaminadoDto {

	private Long idDictaminado;
	
	private Long idDictamen;
	
	private Long idContrato;
		
	private Long idServicioContrato;
	
	private String grupoServicio;

    private String conseptosServico;

    private String unidadMedida;

    private String tipoConsumo;
    
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    private BigDecimal precioUnitario;

    private BigDecimal cantidadServiciosVigente;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    private BigDecimal montoMaximoServicio;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Digits(integer = 20, fraction = 6)
	private BigDecimal cantidadServiciosSat; 
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal cantidadServiciosCc; 
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal cantidadTotalServicios;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal montoDictaminado;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal cantidadServicioDictaminadoAcumulado;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal montonDictaminadoAcumulado;
	
	private BigDecimal porcentajeServiciosDictaminadosAcumulados;
	
	private BigDecimal porcentajeMontoDictaminadosAcumulados;
	
	private String colorPorcentajetServicios;
	
	private String colorPorcentajeMonto;
	
	private String ultimaModificacion;
	
	private Boolean checked;
	
	private Boolean estatusCM;
	
	private Long idConvenioModificatorio;
	
	private Integer orden;

	@Override
	public String toString() {
	    return String.join("|",
	        "idDictaminado:" + idDictaminado,
	        "idDictamen:" + idDictamen,
	        "idContrato:" + idContrato,
	        "idServicioContrato:" + idServicioContrato,
	        "grupoServicio:" + grupoServicio,
	        "conseptosServico:" + conseptosServico,
	        "unidadMedida:" + unidadMedida,
	        "tipoConsumo:" + tipoConsumo,
	        "precioUnitario:" + precioUnitario,
	        "cantidadServiciosVigente:" + cantidadServiciosVigente,
	        "montoMaximoServicio:" + montoMaximoServicio,
	        "cantidadServiciosSat:" + cantidadServiciosSat,
	        "cantidadServiciosCc:" + cantidadServiciosCc,
	        "cantidadTotalServicios:" + cantidadTotalServicios,
	        "montoDictaminado:" + montoDictaminado,
	        "cantidadServicioDictaminadoAcumulado:" + cantidadServicioDictaminadoAcumulado,
	        "montonDictaminadoAcumulado:" + montonDictaminadoAcumulado,
	        "porcentajeServiciosDictaminadosAcumulados:" + porcentajeServiciosDictaminadosAcumulados,
	        "porcentajeMontoDictaminadosAcumulados:" + porcentajeMontoDictaminadosAcumulados,
	        "checked:" + checked,
	        "estatusCM:" + estatusCM
	    ) + "|";
	}


	
}
