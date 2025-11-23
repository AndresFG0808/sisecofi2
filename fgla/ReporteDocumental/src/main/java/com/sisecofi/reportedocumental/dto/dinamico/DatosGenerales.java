package com.sisecofi.reportedocumental.dto.dinamico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Builder
public class DatosGenerales {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean id;

	@NameField(nameField = "nombre_corto", order = 2, nombreFront = "Nombre corto")
	private boolean nombreCorto;

	@NameField(nameField = "s10.nombre", fieldComposite = true, order = 3, nombreFront = "Estatus")
	private boolean estatus;

	@NameField(nameField = "nombre_proyecto", order = 4, nombreFront = "Nombre del proyecto")
	private boolean nombreProyecto;

	@NameField(nameField = "id_agp", order = 5, nombreFront = "Id AGP")
	private boolean idAgp;

	@JsonIgnore
	private boolean requerido;
}
