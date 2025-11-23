package com.sisecofi.libreria.comunes.model.plantillador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import com.sisecofi.libreria.comunes.util.interfaces.Idable;
import com.sisecofi.libreria.comunes.util.interfaces.Unicable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "tipoPlantillador")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatTipoPlantillador extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idTipoPlantillador;

	@Column(name = "nombre", length = 150)
	@CampoReporte(nombre = "nombre")
	private String nombre;

	@Column(name = "descripcion", length = 150)
	@CampoReporte(nombre = "descripcion")
	private String descripcion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPlantillador", fetch = FetchType.EAGER)
	private List<EtiquetaPlantilladorModel> etiquetas;

	public List<EtiquetaPlantilladorModel> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<EtiquetaPlantilladorModel> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public Integer getIdTipoPlantillador() {
		return idTipoPlantillador;
	}

	public void setIdTipoPlantillador(Integer idTipoPlantillador) {
		this.idTipoPlantillador = idTipoPlantillador;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		mapa.put("nombre", nombre);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idTipoPlantillador;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
