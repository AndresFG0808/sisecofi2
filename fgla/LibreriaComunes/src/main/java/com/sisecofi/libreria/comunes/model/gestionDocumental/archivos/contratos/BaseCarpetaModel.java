package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class BaseCarpetaModel {

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "orden")
	private int orden;

	@Column(name = "nivel")
	private int nivel;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "ruta")
	private String ruta;

	@Column(name = "obligatorio")
	private boolean obligatorio;

	@Column(name = "estatus")
	private boolean estatus;
	
	@Column(name = "estatus_carpeta")
	private boolean estatusCarpeta;
}
