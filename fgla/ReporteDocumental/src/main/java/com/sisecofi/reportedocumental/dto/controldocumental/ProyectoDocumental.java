package com.sisecofi.reportedocumental.dto.controldocumental;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class ProyectoDocumental {

	private Long idProyecto;
	private String proyecto;
	private String nombreCorto;
	private String fase;
	private String plantilla;
	private String descripcion;
	private String requerido;
	private boolean noAplica;
	private String estatus;
	private String justificacion;
	private String fechaModificacion;
	private boolean cargado;
	private boolean esPdf;
	private String pathServicio;
	@JsonIgnore	
	private String rutaSatCloud;
	private Long idReferencia;
	
	private Long identificador;
}
