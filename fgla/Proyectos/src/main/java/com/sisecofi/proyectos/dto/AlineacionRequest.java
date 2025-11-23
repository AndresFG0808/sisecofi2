package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AlineacionRequest extends RepresentationModel<AlineacionRequest> {
	@EqualsAndHashCode.Include
	private Integer idMapa;
	private Integer idPeriodo;
	private Integer idObjetivo;
	private Long idFichaAlineacion;
}
