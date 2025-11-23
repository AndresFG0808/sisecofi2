package com.sisecofi.libreria.comunes.model.proyectos;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Embeddable
public class UsuarioAsignadoPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id_proyecto", nullable = false)
	private Long idProyecto;
	@Column(name = "id_user", nullable = false)
	private Long idUser;

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProyecto, idUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioAsignadoPk other = (UsuarioAsignadoPk) obj;
		return Objects.equals(idProyecto, other.idProyecto) && Objects.equals(idUser, other.idUser);
	}

}
