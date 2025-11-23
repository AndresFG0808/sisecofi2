package com.sisecofi.admingeneral.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum EstatusUsuarioEnum {

	ACTIVO("Activo", true), INACTIVO("Inactivo", false);

	private String descripcion;
	private boolean estatus;

	EstatusUsuarioEnum(String descripcion, boolean estatus) {
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@SuppressWarnings("unused")
	private void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstatus() {
		return estatus;
	}

	@SuppressWarnings("unused")
	private void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

}
