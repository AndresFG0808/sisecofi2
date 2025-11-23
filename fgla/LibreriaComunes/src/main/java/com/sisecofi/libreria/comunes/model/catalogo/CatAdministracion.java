package com.sisecofi.libreria.comunes.model.catalogo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoOculto;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "administracion")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@CatalogoComplementario
public class CatAdministracion extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 0, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idAdministracion;

	@CampoFront(nombre = "Administración", tipoDato = "String", orden = 1, tamanio = "150")
	@Column(name = "administracion", length = 150, nullable = false)
	@CampoUnico
	@CampoReporte(nombre = "Área")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_150, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_150)
	private String administracion;

	@CampoFront(nombre = "Acrónimo", tipoDato = "String", orden = 2, tamanio = "20")
	@Column(name = "acronimo", length = 20, nullable = false)
	@CampoUnico
	@CampoReporte(nombre = "Acrónimo")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_20, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_20)
	private String acronimo;

	@CampoFront(nombre = "Puesto", tipoDato = "String", orden = 4, tamanio = "150")
	@Column(name = "puesto", length = 150, nullable = false)
	@CampoUnico
	@CampoReporte(nombre = "Puesto")
	@Pattern(regexp = ErroresEnum.MensajeValidation.REGEX_SOLO_TEXTO_150, message = ErroresEnum.MensajeValidation.MENSAJE_REGEX_SOLO_TEXTO_150)
	private String puesto;

	@ManyToOne
	@JoinColumn(name = "id_admon_central", foreignKey = @ForeignKey(name = "FK_id_admon_central"), nullable = false)
	@CampoFront(nombre = "catalogo-administración-central", tipoDato = "catalogo", orden = 3, id = true)
	@CampoUnico
	private CatAdmonCentral catAdmonCentral;

	@OneToMany(mappedBy = "catAdministracion")
	@Filter(name = "estatusAdminAdministracion")
	@CampoOculto
	@CampoFront(nombre = "Administrador", tipoDato = "String", orden = 3)
	@CampoReporte(nombre = "Administrador de área")
	private List<CatAdministradorAdministracion> administradores;

	public Integer getIdAdministracion() {
		return idAdministracion;
	}

	public void setIdAdministracion(Integer idAdministracion) {
		this.idAdministracion = idAdministracion;
	}

	public CatAdmonCentral getCatAdmonCentral() {
		return catAdmonCentral;
	}

	public void setCatAdmonCentral(CatAdmonCentral catAdmonCentral) {
		this.catAdmonCentral = catAdmonCentral;
	}

	public String getAdministracion() {
		return administracion != null ? administracion.trim() : "";
	}

	public void setAdministracion(String administracion) {
		this.administracion = administracion != null ? administracion.trim() : "";
	}

	public String getAcronimo() {
		return acronimo != null ? acronimo.trim() : "";
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo != null ? acronimo.trim() : "";
	}

	public String getPuesto() {
		return puesto != null ? puesto.trim() : "";
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto != null ? puesto.trim() : "";
	}

	public List<CatAdministradorAdministracion> getAdministradores() {
	      if (administradores != null) {
	          return administradores.stream()
	                  .sorted(Comparator.comparing(CatAdministradorAdministracion::getIdAdministradorAdministracion))
	                  .toList();
	      }
	      return Collections.emptyList();
	  }

	public void setAdministradores(List<CatAdministradorAdministracion> administradores) {
		this.administradores = administradores;
	}

	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("administracion", administracion);
		mapa.put("acronimo", acronimo);
		mapa.put("puesto", puesto);
		mapa.put("catAdmonCentral.idAdmonCentral", catAdmonCentral.getIdAdmonCentral());
		return mapa;
	}

	@Override
	public Object returnId() {
		return idAdministracion;
	}

	@Override
	public String toString() {
		return administracion;
	}
	
	public String obtenerAdministrador() {
	    return administradores.stream()
	        .filter(CatAdministradorAdministracion::isEstatus)
	        .map(CatAdministradorAdministracion::getAdministrador)
	        .findFirst()
	        .orElse(null);
	}

}
