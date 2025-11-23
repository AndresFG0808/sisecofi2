package com.sisecofi.libreria.comunes.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
public class ErrorInfo {

	private final LocalDateTime fecha;
	private final int estatus;
	private final String error;
	private final String traza;
	public final List<String> mensaje;
	public final String url;

	public ErrorInfo(LocalDateTime fecha, int estatus, String error, String traza, List<String> mensaje, String url) {
		super();
		this.fecha = fecha;
		this.estatus = estatus;
		this.error = error;
		this.traza = traza;
		this.mensaje = mensaje;
		this.url = url;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public int getEstatus() {
		return estatus;
	}

	public String getError() {
		return error;
	}

	public String getTraza() {
		return traza;
	}

	public List<String> getMensaje() {
		return mensaje;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "ErrorInfo [fecha=" + fecha + ", estatus=" + estatus + ", error=" + error + ", traza=" + traza
				+ ", mensaje=" + mensaje + ", url=" + url + "]";
	}

}
