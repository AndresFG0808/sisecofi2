package com.sisecofi.proyectos.dto;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;

import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class FichaRequest extends RepresentationModel<FichaRequest> {
	@EqualsAndHashCode.Include
	private Set<HistoricoModel> lideres;
	@Valid
	private FichaTecnicaModel ficha;
	private Set<Long> lideresEliminados;
	private Set<Long> alineacionesEliminadas;
	private List<AlineacionRequest> alineaciones;
}
