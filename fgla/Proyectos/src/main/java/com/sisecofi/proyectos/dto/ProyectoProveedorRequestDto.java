package com.sisecofi.proyectos.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.interfaces.ValidacionIncompleta;

import lombok.Data;

@Data
public class ProyectoProveedorRequestDto {

	private Long id;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups = ValidacionIncompleta.class)
	private Long idProyecto;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups = ValidacionIncompleta.class)
	private Long idProveedor;

	private Integer idInvestigacionMercado;

	private LocalDate fechaIM;

	private Integer respuestaIm;

	private LocalDate fechaRespuestaIM;

	private Integer juntaAclaracion;

	private LocalDate fechaJA;

	private Integer licitacionPublica;

	private LocalDate fechaLP;
	
	private String comentario;
}
