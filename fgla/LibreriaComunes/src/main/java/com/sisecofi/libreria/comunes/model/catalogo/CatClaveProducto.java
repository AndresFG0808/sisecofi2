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
@Table(name = ConstantesComunes.PREFIX_CAT + "claveProducto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@CatalogoGeneral
public class CatClaveProducto extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idClaveProducto;


	@CampoFront(nombre = "Clave", tipoDato = "String", orden = 2, tamanio = "300")
	@Column(name = "clave", length = 300)
	@CampoReporte(nombre = "Clave")
	@CampoMostrar
	@CampoUnico
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_300, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_300)
	private String clave;

	public Integer getIdClaveProducto() {
		return idClaveProducto;
	}

	public void setIdClaveProducto(Integer idClaveProducto) {
		this.idClaveProducto = idClaveProducto;
	}


	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("clave", clave);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idClaveProducto;
	}

}
