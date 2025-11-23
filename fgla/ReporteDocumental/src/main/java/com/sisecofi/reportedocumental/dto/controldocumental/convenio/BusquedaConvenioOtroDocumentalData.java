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
public class BusquedaConvenioOtroDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoConvenioOtro;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoConvenioOtro;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoConvenioOtro;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseConvenioOtro;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaConvenioOtro;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionConvenioOtro;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoConvenioOtro;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaConvenioOtro;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusConvenioOtro;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionConvenioOtro;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionConvenioOtro;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoConvenioOtro;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaConvenioOtro;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoConvenioOtro;

	@NameField(nameField = "11", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorConvenioOtro;

	public BusquedaConvenioOtroDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoConvenioOtro(true);
	    setProyectoConvenioOtro(true);
	    setNombreCortoConvenioOtro(true);
	    setFaseConvenioOtro(true);
	    setPlantillaConvenioOtro(true);
	    setDescripcionConvenioOtro(true);
	    setRequeridoConvenioOtro(true);
	    setNoAplicaConvenioOtro(true);
	    setEstatusConvenioOtro(true);
	    setJustificacionConvenioOtro(true);
	    setFechaModificacionConvenioOtro(true);
	    setCargadoConvenioOtro(true);
	    setRutaConvenioOtro(true);
	    setIdArchivoConvenioOtro(true);
	    setIdentificadorConvenioOtro(true);
	}

}
