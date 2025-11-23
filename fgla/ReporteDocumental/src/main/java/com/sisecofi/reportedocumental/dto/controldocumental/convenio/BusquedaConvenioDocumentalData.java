package com.sisecofi.reportedocumental.dto.controldocumental.convenio;

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
public class BusquedaConvenioDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoConvenio;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoConvenio;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoConvenio;

	@NameField(nameField = "s10.nombre", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseConvenio;

	@NameField(nameField = "s4.nombre", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaConvenio;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionConvenio;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoConvenio;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaConvenio;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusConvenio;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionConvenio;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionConvenio;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoConvenio;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaConvenio;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoConvenio;

	@NameField(nameField = "9", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorConvenio;

	public BusquedaConvenioDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoConvenio(true);
	    setProyectoConvenio(true);
	    setNombreCortoConvenio(true);
	    setFaseConvenio(true);
	    setPlantillaConvenio(true);
	    setDescripcionConvenio(true);
	    setRequeridoConvenio(true);
	    setNoAplicaConvenio(true);
	    setEstatusConvenio(true);
	    setJustificacionConvenio(true);
	    setFechaModificacionConvenio(true);
	    setCargadoConvenio(true);
	    setRutaConvenio(true);
	    setIdArchivoConvenio(true);
	    setIdentificadorConvenio(true);
	}

}
