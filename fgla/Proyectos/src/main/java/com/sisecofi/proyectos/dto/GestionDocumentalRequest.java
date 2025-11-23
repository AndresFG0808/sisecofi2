package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;


@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class GestionDocumentalRequest extends RepresentationModel<GestionDocumentalRequest> {
	@EqualsAndHashCode.Include
	List <ArchivoFaseDto> archivos;
	List <Archivo> eliminados;
}
