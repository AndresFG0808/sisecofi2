package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ProyectoTablaMetaDto extends RepresentationModel<ProyectoTablaMetaDto> {
	@EqualsAndHashCode.Include
	private String nombreCorto;
	private Long idProyecto;
	private Integer estatus;
	private String liderProyecto;
	private Integer areaSolicitante;
	private Integer areaResponsable;
	private LocalDate fechaFin;
	private LocalDate fechaIncio;
	private double montoSolicitado;
}
