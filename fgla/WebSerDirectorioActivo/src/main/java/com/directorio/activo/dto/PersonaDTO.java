package com.directorio.activo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class PersonaDTO {

	@JsonProperty("rfc_CORTO")
	private String rfcCorto;

	@JsonProperty("nombre_COMPLETO")
	private String nombreCompleto;

	@JsonProperty("un_ADMIN")
	private String unAdmin;

	@JsonProperty("desc_UN_ADMIN")
	private String descUnAdmin;

	private String nombre;
	
	private String rfc;

	@JsonProperty("apellido_PATERNO")
	private String apellidoPaterno;

	@JsonProperty("apellido_MATERNO")
	private String apellidoMaterno;

	public String getRfcCorto() {
		return rfcCorto;
	}

	public void setRfcCorto(String rfcCorto) {
		this.rfcCorto = rfcCorto;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getUnAdmin() {
		return unAdmin;
	}

	public void setUnAdmin(String unAdmin) {
		this.unAdmin = unAdmin;
	}

	public String getDescUnAdmin() {
		return descUnAdmin;
	}

	public void setDescUnAdmin(String descUnAdmin) {
		this.descUnAdmin = descUnAdmin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@Override
	public String toString() {
		return "PersonaDTO [rfcCorto=" + rfcCorto + ", nombreCompleto=" + nombreCompleto + ", unAdmin=" + unAdmin
				+ ", descUnAdmin=" + descUnAdmin + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + "]";
	}

}
