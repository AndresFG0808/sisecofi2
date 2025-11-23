package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ObtenerPenasDeduccionResponseDto {

	private Long idDeduccion;
	private Integer idTipoDeduccion;
	private String tipoDeduccionNombre;

	private Long idServiciosSla;
	private String slaNombre;

	private Long idPeriodicos;
	private String periodicosNombre;

	private Long idInformeSevicios;
	private String informeSeviciosNombre;

	private Long idInformeUv;
	private String informeUvNombre;

	private Long idAtraso;
	private String atrasoNombre;

	private Long idDictamen;
	private BigDecimal monto;
	private String conceptoServicio;
	private String penaAplicable;
	private String desgloceNombre;

}
