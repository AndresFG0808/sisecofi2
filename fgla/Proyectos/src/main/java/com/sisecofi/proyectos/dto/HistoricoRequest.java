package com.sisecofi.proyectos.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class HistoricoRequest extends RepresentationModel<HistoricoRequest> {
	@EqualsAndHashCode.Include
	private int page;
	private int size;
}
