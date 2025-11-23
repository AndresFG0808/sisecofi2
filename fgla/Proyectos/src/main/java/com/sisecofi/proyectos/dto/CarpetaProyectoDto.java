package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CarpetaProyectoDto extends RepresentationModel<CarpetaProyectoDto> {
	@EqualsAndHashCode.Include
	private String nombre;
	private int orden;
	private int nivel;
	private String tipo;
	private boolean obligatorio;
	private boolean estatus;
	private String descripcion;
	@JsonInclude(Include.NON_NULL)
	private Integer idCatalogo;
	@JsonInclude(Include.NON_NULL)
	private List<CarpetaPlantillaModel> carpetas;

	@Override
	public @NonNull String toString() {
		return "CarpetaDto [nombre=" + nombre + ", orden=" + orden + ", nivel=" + nivel + "]";
	}
}
