package com.sisecofi.reportedocumental.dto.controldocumental.reintegros;

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
public class BusquedaReintegroOtroDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoReintegroOtro;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoReintegroOtro;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoReintegroOtro;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseReintegroOtro;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaReintegroOtro;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionReintegroOtro;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoReintegroOtro;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaReintegroOtro;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusReintegroOtro;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionReintegroOtro;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionReintegroOtro;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoReintegroOtro;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaReintegroOtro;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoReintegroOtro;

	@NameField(nameField = "14", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorReintegroOtro;

	public BusquedaReintegroOtroDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoReintegroOtro(true);
	    setProyectoReintegroOtro(true);
	    setNombreCortoReintegroOtro(true);
	    setFaseReintegroOtro(true);
	    setPlantillaReintegroOtro(true);
	    setDescripcionReintegroOtro(true);
	    setRequeridoReintegroOtro(true);
	    setNoAplicaReintegroOtro(true);
	    setEstatusReintegroOtro(true);
	    setJustificacionReintegroOtro(true);
	    setFechaModificacionReintegroOtro(true);
	    setCargadoReintegroOtro(true);
	    setRutaReintegroOtro(true);
	    setIdArchivoReintegroOtro(true);
	    setIdentificadorReintegroOtro(true);
	}

}
