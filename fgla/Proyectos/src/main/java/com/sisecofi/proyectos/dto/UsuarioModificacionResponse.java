package com.sisecofi.proyectos.dto;



import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UsuarioModificacionResponse extends RepresentationModel<UsuarioModificacionResponse>{
	@EqualsAndHashCode.Include
	private String nombre;
	private LocalDateTime fechaModificacion; 
}
