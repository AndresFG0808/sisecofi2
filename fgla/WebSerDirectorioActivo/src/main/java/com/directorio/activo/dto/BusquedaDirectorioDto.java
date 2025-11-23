package com.directorio.activo.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
public class BusquedaDirectorioDto {

	@SerializedName("rfc_corto")
	private String rfcCorto;

	private String nombre;

	@SerializedName("apellidoPaterno")
	private String apellidoPaterno;

	@SerializedName("apellidoMaterno")
	private String apellidoMaterno;

	public String getRfcCorto() {
		return rfcCorto;
	}

	public void setRfcCorto(String rfcCorto) {
		this.rfcCorto = rfcCorto;
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

	@Override
	public String toString() {
		return "BusquedaDirectorioDto [rfcCorto=" + rfcCorto + ", nombre=" + nombre + ", apellidoPaterno="
				+ apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + "]";
	}

}
