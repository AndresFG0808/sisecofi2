package com.sisecofi.libreria.comunes.model.catalogo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import com.sisecofi.libreria.comunes.util.anotaciones.CatalogoComplementario;
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
@Table(name = ConstantesComunes.PREFIX_CAT + "empleadoAdministracion")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@CatalogoComplementario
public class CatEmpleadoAdministracion extends BaseCatalogoModel implements Unicable, Idable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@CampoFront(nombre = "ID", tipoDato = "Integer", orden = 1, id = true)
	@JsonProperty("primaryKey")
	@CampoReporte(nombre = "Id")
	private Integer idEmpleadoAdministracion;

	@CampoFront(nombre = "nombre", tipoDato = "String", orden = 2)
	@Column(name = "nombre", length = 150)
	@CampoReporte(nombre = "Nombre completo")
	private String nombre;

	@CampoFront(nombre = "Fecha inicio vigencia", tipoDato = "Date", orden = 3)
	@Column(name = "fechaInicioVigencia")
	@CampoReporte(nombre = "Fecha inicio de vigencia")
	private LocalDateTime fechaInicioVigencia;

	@CampoFront(nombre = "Fecha fin vigencia", tipoDato = "Date", orden = 4)
	@Column(name = "fechaFinVigencia")
	@CampoReporte(nombre = "Fecha fin de vigencia")
	private LocalDateTime fechaFinVigencia;

	@ManyToOne
	@JoinColumn(name = "id_administracion", foreignKey = @ForeignKey(name = "FK_id_admon_administracion"), nullable = false)
	@CampoFront(nombre = "catalogo-administración", tipoDato = "catalogo", orden = 8, id = true)
	@JsonBackReference
	private CatAdministracion catAdministracion;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_empleado", foreignKey = @ForeignKey(name = "FK_id_tipo_empleado"), nullable = false)
	@CampoFront(nombre = "catalogo-empleado", tipoDato = "catalogo", orden = 9, id = true)
	@JsonBackReference
	private CatTipoEmpleado catTipoEmpleado;
	
	@CampoFront(nombre = "Correo", tipoDato = "String", orden = 3)
	@Column(name = "correo", length = 150)
	@CampoReporte(nombre = "Correo")
	private String correo;
	
	@CampoFront(nombre = "Telefono", tipoDato = "String", orden = 4)
	@Column(name = "telefono", length = 150)
	@CampoReporte(nombre = "Telefono")
	private String telefono;


	public Integer getIdEmpleadoAdministracion() {
		return idEmpleadoAdministracion;
	}

	public void setIdEmpleadoAdministracion(Integer idEmpleadoAdministracion) {
		this.idEmpleadoAdministracion = idEmpleadoAdministracion;
	}

	public CatAdministracion getCatAdministracion() {
		return catAdministracion;
	}

	public void setCatAdministracion(CatAdministracion catAdministracion) {
		this.catAdministracion = catAdministracion;
	}
	
	public LocalDateTime getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(LocalDateTime fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	public LocalDateTime getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(LocalDateTime fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}
	
	@Transient
    private boolean nuevo;

	@Override
	public Map<String, Object> returnObject() {
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("nombre", nombre);
		return mapa;
	}

	@Override
	public Object returnId() {
		return idEmpleadoAdministracion;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	
	@Override
	public boolean isEstatus() {
		return getEstatusVal(); 
	}
	
	public String getTelefono() {
		return telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CatTipoEmpleado getCatTipoEmpleado() {
		return catTipoEmpleado;
	}

	public void setCatTipoEmpleado(CatTipoEmpleado catTipoEmpleado) {
		this.catTipoEmpleado = catTipoEmpleado;
	}

}
