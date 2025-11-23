package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;


@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AsociacionResponse extends RepresentationModel<AsociacionResponse> {
	@EqualsAndHashCode.Include
	private Long idAsociacion;
	private PlantillaVigenteModel plantilla;
	private CatFaseProyecto fase;
	private LocalDate fechaAsignacion;
	private List<PlantillaVigenteModel> plantillasOpciones;
	private Integer orden;
	private boolean cargado;
}
