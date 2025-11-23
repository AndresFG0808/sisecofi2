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
public class BusquedaReintegroDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoReintegro;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoReintegro;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoReintegro;

	@NameField(nameField = "s10.nombre", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseReintegro;

	@NameField(nameField = "s4.nombre", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaReintegro;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionReintegro;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoReintegro;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaReintegro;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusReintegro;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionReintegro;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionReintegro;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoReintegro;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaReintegro;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoReintegro;

	@NameField(nameField = "12", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorReintegro;

	public BusquedaReintegroDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoReintegro(true);
	    setProyectoReintegro(true);
	    setNombreCortoReintegro(true);
	    setFaseReintegro(true);
	    setPlantillaReintegro(true);
	    setDescripcionReintegro(true);
	    setRequeridoReintegro(true);
	    setNoAplicaReintegro(true);
	    setEstatusReintegro(true);
	    setJustificacionReintegro(true);
	    setFechaModificacionReintegro(true);
	    setCargadoReintegro(true);
	    setRutaReintegro(true);
	    setIdArchivoReintegro(true);
	    setIdentificadorReintegro(true);
	}

}
