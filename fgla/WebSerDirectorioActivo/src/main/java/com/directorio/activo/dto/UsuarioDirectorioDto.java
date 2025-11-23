package com.directorio.activo.dto;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class UsuarioDirectorioDto {

	private String nombreCompleto;
	private String puesto;
	private String rfcCorto;
	private String administracion;
	private String unAdmin;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getRfcCorto() {
		return rfcCorto;
	}

	public void setRfcCorto(String rfcCorto) {
		this.rfcCorto = rfcCorto;
	}

	public String getAdministracion() {
		return administracion;
	}

	public void setAdministracion(String administracion) {
		this.administracion = administracion;
	}

	public String getUnAdmin() {
		return unAdmin;
	}

	public void setUnAdmin(String unAdmin) {
		this.unAdmin = unAdmin;
	}
	
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@Override
	public String toString() {
		return "UsuarioDirectorioDto [nombreCompleto=" + nombreCompleto + ", puesto=" + puesto + ", rfcCorto="
				+ rfcCorto + ", administracion=" + administracion + ", unAdmin=" + unAdmin + "]";
	}

}
