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
@SuppressWarnings("rawtypes")
public class ArchivosDto extends RepresentationModel<ArchivosDto> {
	
	public ArchivosDto(List<Archivo> archivos, List<Archivo> otrosDocumentos) {
		super();
		this.archivos = archivos;
		this.otrosDocumentos = otrosDocumentos;
	}
	@EqualsAndHashCode.Include
	private List<Archivo> archivos;
	private List<Archivo> otrosDocumentos;
}
