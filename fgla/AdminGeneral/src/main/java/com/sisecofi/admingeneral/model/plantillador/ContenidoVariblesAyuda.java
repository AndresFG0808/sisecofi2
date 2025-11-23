package com.sisecofi.admingeneral.model.plantillador;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "contenidoVariables")
@Getter
@Setter
public class ContenidoVariblesAyuda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idContenidoVariables;

	@Column(name = "contenido", nullable = false, length = 2000000)
	private String contenido;
	

}
