package com.sisecofi.proyectos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;


@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ArchivoFaseDto extends RepresentationModel<ArchivoFaseDto> {
	@EqualsAndHashCode.Include
	private String nombreFase;
	private Long idProyecto;
	@SuppressWarnings("rawtypes")
	private Archivo archivo;
	private MultipartFile file;
	private String nombreFile;
}
