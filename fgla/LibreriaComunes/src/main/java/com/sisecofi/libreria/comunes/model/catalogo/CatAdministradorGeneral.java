package com.sisecofi.libreria.comunes.model.catalogo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.FilterDef;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoUnico;
import com.sisecofi.libreria.comunes.util.anotaciones.CatalogoComplementario;
import com.sisecofi.libreria.comunes.util.interfaces.AdministradorBase;
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
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "administradorGeneral")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@FilterDef(name = "estatusAdminGeneral", defaultCondition = "estatus = true")
@CatalogoComplementario
public class CatAdministradorGeneral extends BaseCatalogoModel implements Unicable, Idable, AdministradorBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idAdministradorGeneral;

	@CampoFront(nombre = "administrador", tipoDato = "String", orden = 2)
	@Column(name = "administrador", length = 150)
	@CampoUnico
	@CampoReporte(nombre = "Nombre completo")
	private String administrador;

	@CampoFront(nombre = "Fecha inicio vigencia", tipoDato = "Date", orden = 3)
	@Column(name = "fechaInicioVigencia")
	@CampoReporte(nombre = "Fecha inicio de vigencia")
	private LocalDateTime fechaInicioVigencia;

	@CampoFront(nombre = "Fecha fin vigencia", tipoDato = "Date", orden = 4)
	@Column(name = "fechaFinVigencia")
	@CampoReporte(nombre = "Fecha fin de vigencia")
	private LocalDateTime fechaFinVigencia;

	@ManyToOne
	@JoinColumn(name = "id_admon_general", foreignKey = @ForeignKey(name = "FK_id_admon_general"), nullable = false)
	@CampoFront(nombre = "catalogo-administración-general", tipoDato = "catalogo", orden = 8, id = true)
	@JsonBackReference
	private CatAdmonGeneral catAdmonGeneral;
	
	@CampoFront(nombre = "Correo", tipoDato = "String", orden = 3)
	@Column(name = "correo", length = 150)
	@CampoUnico
	@CampoReporte(nombre = "Correo")
	private String correo;
	
	@CampoFront(nombre = "Telefono", tipoDato = "String", orden = 4)
	@Column(name = "telefono", length = 150)
	@CampoUnico
	@CampoReporte(nombre = "Telefono")
	private String telefono;

	@Override
	public String getAdministrador() {
		return administrador != null ? administrador.trim() : "";
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador != null ? administrador.trim() : "";
	}

	public Integer getIdAdministradorGeneral() {
		return idAdministradorGeneral;
	}

	public void setIdAdministradorGeneral(Integer idAdministradorGeneral) {
		this.idAdministradorGeneral = idAdministradorGeneral;
	}

	public CatAdmonGeneral getCatAdmonGeneral() {
		return catAdmonGeneral;
	}

	public void setCatAdmonGeneral(CatAdmonGeneral catAdmonGeneral) {
		this.catAdmonGeneral = catAdmonGeneral;
	}

	@Override
	public LocalDateTime getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(LocalDateTime fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	@Override
	public LocalDateTime getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	
	public void setFechaFinVigencia(LocalDateTime fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	
	
	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("administrador", administrador);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idAdministradorGeneral;
	}
	
	@Transient
    private boolean nuevo;

	@Override
	public String toString() {
		return administrador;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	@Override
	public Integer getIdAdministrador() {
		return getIdAdministradorGeneral();
	}
	
	@Override
	public boolean isEstatus() {
		return getEstatusVal(); 
	}
	
	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
