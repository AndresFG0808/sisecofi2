package com.sisecofi.proyectos.dto;

import java.time.LocalDate;

import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.interfaces.ValidacionIncompleta;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProyectoProveedorResponseDto {

	private Long id;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups = ValidacionIncompleta.class)
	private Long idProyecto;

	private String proyectoNombreCorto;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ, groups = ValidacionIncompleta.class)
	private Long idProveedor;

	private String proveedorNombre;

	private Integer idInvestigacionMercado;

	private LocalDate fechaIm;

	private Integer respuestaIm;

	private LocalDate fechaRespuestaIm;

	private Integer juntaAclaracion;

	private LocalDate fechaJa;

	private Integer licitacionPublica;

	private LocalDate fechaLp;
	
	private String comentario;
	
	

}
