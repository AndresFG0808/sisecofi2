package com.sisecofi.libreria.comunes.model.usuario;

import java.util.Objects;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "rol")
public class RolModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRol;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

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
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolModel other = (RolModel) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "RolModel [idRol=" + idRol + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

}
