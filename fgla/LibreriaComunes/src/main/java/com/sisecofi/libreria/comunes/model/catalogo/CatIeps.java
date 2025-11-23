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
import com.sisecofi.libreria.comunes.util.anotaciones.CatalogoGeneral;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.Idable;
import com.sisecofi.libreria.comunes.util.interfaces.Unicable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "ieps")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@CatalogoGeneral
public class CatIeps extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@EqualsAndHashCode.Include
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idIeps;

	@CampoFront(nombre = "Porcentaje", tipoDato = "String", orden = 2, tamanio = "150")
	@Column(name = "porcentaje", length = 150)
	@CampoMostrar
	@CampoUnico
	@CampoReporte(nombre = "Porcentaje")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_150, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_150)
	private String porcentaje;

	@CampoFront(nombre = "Descripción", tipoDato = "String", orden = 3, tamanio = "300")
	@Column(name = "descripcion", length = 300)
	@CampoReporte(nombre = "Descripción")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_300, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_300)
	private String descripcion;

	public Integer getIdIeps() {
		return idIeps;
	}

	public void setIdIeps(Integer idIeps) {
		this.idIeps = idIeps;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}


	public String getPorcentaje() {
		return porcentaje;
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
		mapa.put("porcentaje", porcentaje);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idIeps;
	}
}
