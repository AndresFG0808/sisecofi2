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
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class DataDto implements ComparableById {

	private Integer idTarea;
	private Integer nivelEsquema;
	private String nombreTarea;

	private boolean activo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")
	private Double duracionPlaneada;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaInicioPlaneada;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaFinPlaneada;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")
	private Double duracionReal;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaInicioReal;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaFinReal;

	private Integer predecesora;
	private Integer planeado;
	private Integer completado;
}
