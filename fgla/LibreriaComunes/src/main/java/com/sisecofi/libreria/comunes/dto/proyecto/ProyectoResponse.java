package com.sisecofi.libreria.comunes.dto.proyecto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = false, callSuper = false)
public class ProyectoResponse extends RepresentationModel<ProyectoResponse> {
	private ProyectoModel proyecto;
	private CatEstatusProyecto estatus;
	private Set<BaseCatalogoModel> opcionesEstatus;
	private BaseCatalogoModel estatusCancelado;
	private LocalDateTime ultimaFechaModificacion;
	private String nombreUsuario;
}
