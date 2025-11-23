package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;

import jakarta.validation.Valid;


@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AsociacionGuardarRequest extends RepresentationModel<AsociacionGuardarRequest> {
	@EqualsAndHashCode.Include
	@Valid
	Set<AsociacionesModel> asociacionesNuevas;
	@Valid
	Set<AsociacionesModel> asociacionesModificadas;
	Set<Long> asociacionesEliminadas;
}
