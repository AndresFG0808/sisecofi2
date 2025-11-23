package com.sisecofi.libreria.comunes.model.plantillador;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
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
import lombok.EqualsAndHashCode;



@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "subTipoPlantillador")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatSubTipoPlantillador extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Long idSubTipoPlantillador;

	@Column(name = "nombre", length = 150)
	@CampoReporte(nombre = "nombre")
	private String nombre;

	@Column(name = "descripcion", length = 150)
	@CampoReporte(nombre = "descripcion")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_tipo_plantillador"), nullable = true)
	private CatTipoPlantillador catTipoPlantillador;

	public CatTipoPlantillador getCatTipoPlantillador() {
		return catTipoPlantillador;
	}

	public void setCatTipoPlantillador(CatTipoPlantillador catTipoPlantillador) {
		this.catTipoPlantillador = catTipoPlantillador;
	}

	public Long getIdSubTipoPlantillador() {
		return idSubTipoPlantillador;
	}

	public void setIdSubTipoPlantillador(Long idSubTipoPlantillador) {
		this.idSubTipoPlantillador = idSubTipoPlantillador;
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
		return idSubTipoPlantillador;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
