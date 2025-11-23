package com.sisecofi.libreria.comunes.model.catalogo;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoOculto;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import com.sisecofi.libreria.comunes.util.anotaciones.PistaSat;
import com.sisecofi.libreria.comunes.util.interfaces.Fechable;
import com.sisecofi.libreria.comunes.util.interfaces.Nombrable;
import com.sisecofi.libreria.comunes.util.interfaces.Identificable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@MappedSuperclass
@Data
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({ @Type(value = BaseCatalogoModel.class, name = "nombre"),
		@Type(value = BaseCatalogoModel.class, name = "descripcion"),
		@Type(value = BaseCatalogoModel.class, name = "fechaCreacion"),
		@Type(value = BaseCatalogoModel.class, name = "fechaModificacion"),
		@Type(value = BaseCatalogoModel.class, name = "estatus"),
		@Type(value = BaseCatalogoModel.class, name = "primaryKey") })
public class BaseCatalogoModel extends RepresentationModel<BaseCatalogoModel> implements Nombrable, Fechable, Identificable  {

	@CampoFront(nombre = "Fecha de creación", tipoDato = "Date", orden = 5)
	@Column(name = "fechaCreacion", nullable = false)
	@CampoOculto
	@CampoReporte(nombre = "Fecha creación")
	@PistaSat
	private LocalDateTime fechaCreacion;

	@CampoFront(nombre = "Última modificación", tipoDato = "Date", orden = 6)
	@Column(name = "fechaModificacion")
	@CampoOculto
	@CampoReporte(nombre = "Última modificación")
	@PistaSat
	private LocalDateTime fechaModificacion;

	@CampoFront(nombre = "Estatus", tipoDato = "Boolean", orden = 7)
	@Column(name = "estatus", nullable = false, columnDefinition = "boolean default false")
	@CampoReporte(nombre = "Estatus")
	@PistaSat
	private boolean estatus;

	private transient Integer primaryKey;

	public BaseCatalogoModel() {
		//constructor 
	}

	@Override
	public void setNombre(String nombre) {
		//setNombre
	}

	@Override
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public String getNombre() {
		return "";
	}

	@Override
	public Object returnId() {
		return null;
	}
	
	@JsonIgnore
	public boolean getEstatusVal() {
	    return this.estatus;
	}

}
