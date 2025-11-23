package com.sisecofi.proyectos.dto.adminplantrabajo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sisecofi.proyectos.util.functions.ComparableById;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class DataDtoResponse implements ComparableById {

	private Integer idTarea;
	private Integer nivelEsquema;
	private String nombreTarea;

	private boolean activo;

	private Integer duracionPlaneada;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaInicioPlaneada;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaFinPlaneada;

	private Integer duracionReal;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaInicioReal;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaFinReal;

	private Integer predecesora;
	private Integer planeado;
	private Integer completado;
}
