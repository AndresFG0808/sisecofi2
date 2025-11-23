package com.sisecofi.libreria.comunes.model.catalogo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoMostrar;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoUnico;
import com.sisecofi.libreria.comunes.util.anotaciones.CatalogoComplementario;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.Idable;
import com.sisecofi.libreria.comunes.util.interfaces.Unicable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "mapaObjetivo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@CatalogoComplementario
public class CatMapaObjetivo extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idMapaObjetivo;

	@ManyToOne
	@JoinColumn(name = "id_aliniacion", foreignKey = @ForeignKey(name = "FK_aliniacion"), nullable = false)
	@CampoFront(nombre = "catalogo-alineacion", tipoDato = "catalogo", orden = 2, id = true)
	private CatAliniacion catAliniacion;

	@CampoFront(nombre = "Objetivo", tipoDato = "String", orden = 3, tamanio = "150")
	@Column(name = "objetivo", length = 150)
	@CampoMostrar
	@CampoUnico
	@CampoReporte(nombre = "Objetivo")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_150, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_150)
	private String objetivo;

	@CampoFront(nombre = "Descripción", tipoDato = "String", orden = 4, tamanio = "300")
	@Column(name = "descripcion", length = 300)
	@CampoReporte(nombre = "Descripción")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_300, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_300)
	private String descripcion;

	public Integer getIdMapaObjetivo() {
		return idMapaObjetivo;
	}

	public void setIdMapaObjetivo(Integer idMapaObjetivo) {
		this.idMapaObjetivo = idMapaObjetivo;
	}

	public CatAliniacion getCatAliniacion() {
		return catAliniacion;
	}

	public void setCatAliniacion(CatAliniacion catAliniacion) {
		this.catAliniacion = catAliniacion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("objetivo", objetivo);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idMapaObjetivo;
	}

	@Override
	public String toString() {
		return "CatMapaObjetivo [idMapaObjetivo=" + idMapaObjetivo + ", catAliniacion=" + catAliniacion + ", objetivo="
				+ objetivo + ", descripcion=" + descripcion + "]";
	}

}
