package com.sisecofi.libreria.comunes.dto.proyecto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import com.sisecofi.libreria.comunes.model.catalogo.CatAliniacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatMapaObjetivo;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodo;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AlineacionResponse extends RepresentationModel<AlineacionResponse> {
	@EqualsAndHashCode.Include
	private Long id;
	private CatAliniacion mapa;
	private CatPeriodo periodo;
	private CatMapaObjetivo objetivo;
}
