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
@Table(name = ConstantesComunes.PREFIX_CAT + "urlPowerBi")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@CatalogoGeneral
public class CatUrlPowerBi extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@EqualsAndHashCode.Include
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idUrlPowerBi;

	@CampoFront(nombre = "Nombre", tipoDato = "String", orden = 2, tamanio = "150")
	@Column(name = "nombre", length = 150)
	@CampoMostrar
	@CampoUnico
	@CampoReporte(nombre = "Nombre")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_150, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_150)
	private String nombre;

	@CampoFront(nombre = "Url", tipoDato = "String", orden = 3, tamanio = "300")
	@Column(name = "url", length = 300)
	@CampoReporte(nombre = "Url")
	private String url;

	public Integer getIdUrlPowerBi() {
		return idUrlPowerBi;
	}

	public void setIdUrlPowerBi(Integer idUrlPowerBi) {
		this.idUrlPowerBi = idUrlPowerBi;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre != null ? nombre.trim() : null;
	}

	@Override
	public String getNombre() {
		return nombre != null ? nombre.trim() : null;
	}

	

	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("nombre", nombre);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idUrlPowerBi;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
