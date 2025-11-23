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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = ConstantesComunes.PREFIX_CAT + "admonGeneral")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "ignoreUnknown"})
@CatalogoComplementario
public class CatAdmonGeneral extends BaseCatalogoModel implements Unicable, Idable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 0, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idAdmonGeneral;

	@CampoFront(nombre = "Administración", tipoDato = "String", orden = 1, tamanio = "150")
	@Column(name = "administracion", length = 150, nullable = false)
	@CampoUnico
	@CampoReporte(nombre = "Administración")
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

	@OneToMany(mappedBy = "catAdmonGeneral")
	@Filter(name = "estatusAdminGeneral")
	@CampoOculto
	@CampoFront(nombre = "Administrador", tipoDato = "String", orden = 3)
	@CampoReporte(nombre = "Administrador general")
	private List<CatAdministradorGeneral> administradores;

	public Integer getIdAdmonGeneral() {
		return idAdmonGeneral;
	}

	public void setIdAdmonGeneral(Integer idAdmonGeneral) {
		this.idAdmonGeneral = idAdmonGeneral;
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

	public List<CatAdministradorGeneral> getAdministradores() {
	      if (administradores != null) {
	          return administradores.stream()
	                  .sorted(Comparator.comparing(CatAdministradorGeneral::getIdAdministradorGeneral))
	                  .toList();
	      }
	      return Collections.emptyList();
	  }

	public void setAdministradores(List<CatAdministradorGeneral> administradores) {
		this.administradores = administradores;
	}

	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("administracion", administracion);
		mapa.put("acronimo", acronimo);
		mapa.put("puesto", puesto);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idAdmonGeneral;
	}

	@Override
	public String toString() {
		return "CatAdmonGeneral [idAdmonGeneral=" + idAdmonGeneral + ", administracion=" + administracion
				+ ", acronimo=" + acronimo + ", puesto=" + puesto + ", administradores=" + administradores + "]";
	}

	public String obtenerAdministrador() {
	    return administradores.stream()
	        .filter(CatAdministradorGeneral::isEstatus)
	        .map(CatAdministradorGeneral::getAdministrador)
	        .findFirst()
	        .orElse(null);
	}
}
