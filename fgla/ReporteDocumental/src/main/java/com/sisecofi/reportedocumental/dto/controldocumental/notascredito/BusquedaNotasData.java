package com.sisecofi.reportedocumental.dto.controldocumental.notascredito;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
public class BusquedaNotasData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoNotas;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoNotas;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoNotas;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseNotas;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaNotas;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionNotas;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoNotas;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaNotas;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusNotas;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionNotas;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionNotas;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoNotas;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaNotas;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoNotas;

	@NameField(nameField = "19", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorNotas;

	public BusquedaNotasData() {
		inicializarCampos();
	}

	private void inicializarCampos() {
		setIdProyectoNotas(true);
		setProyectoNotas(true);
		setNombreCortoNotas(true);
		setFaseNotas(true);
		setPlantillaNotas(true);
		setDescripcionNotas(true);
		setRequeridoNotas(true);
		setNoAplicaNotas(true);
		setEstatusNotas(true);
		setJustificacionNotas(true);
		setFechaModificacionNotas(true);
		setCargadoNotas(true);
		setRutaNotas(true);
		setIdArchivoNotas(true);
		setIdentificadorNotas(true);
	}

}
