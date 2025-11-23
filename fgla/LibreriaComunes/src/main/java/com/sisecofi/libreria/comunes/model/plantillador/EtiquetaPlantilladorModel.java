package com.sisecofi.libreria.comunes.model.plantillador;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;

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

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "etiquetaPlantillador")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtiquetaPlantilladorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Long idEtiquetaPlantillador;

	@Column(name = "nombreEtiqueta", length = 150)
	@CampoReporte(nombre = "nombreEtiqueta")
	private String nombreEtiqueta;

	@ManyToOne
	@JoinColumn(name = "id_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_tipo_plantillador"), nullable = false)
	@JsonIgnore
	private CatTipoPlantillador tipoPlantillador;

	public Long getIdEtiquetaPlantillador() {
		return idEtiquetaPlantillador;
	}

	public void setIdEtiquetaPlantillador(Long idEtiquetaPlantillador) {
		this.idEtiquetaPlantillador = idEtiquetaPlantillador;
	}

	public String getNombreEtiqueta() {
		return nombreEtiqueta;
	}

	public void setNombreEtiqueta(String nombreEtiqueta) {
		this.nombreEtiqueta = nombreEtiqueta;
	}

	public CatTipoPlantillador getTipoPlantillador() {
		return tipoPlantillador;
	}

	public void setTipoPlantillador(CatTipoPlantillador tipoPlantillador) {
		this.tipoPlantillador = tipoPlantillador;
	}

}
