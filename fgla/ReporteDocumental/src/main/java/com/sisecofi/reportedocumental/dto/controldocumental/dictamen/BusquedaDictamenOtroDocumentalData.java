package com.sisecofi.reportedocumental.dto.controldocumental.dictamen;

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
public class BusquedaDictamenOtroDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoDictamenOtro;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoDictamenOtro;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoDictamenOtro;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseDictamenOtro;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaDictamenOtro;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionDictamenOtro;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoDictamenOtro;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaDictamenOtro;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusDictamenOtro;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionDictamenOtro;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionDictamenOtro;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoDictamenOtro;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaDictamenOtro;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoDictamenOtro;

	@NameField(nameField = "17", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorDictamenOtro;

	public BusquedaDictamenOtroDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoDictamenOtro(true);
	    setProyectoDictamenOtro(true);
	    setNombreCortoDictamenOtro(true);
	    setFaseDictamenOtro(true);
	    setPlantillaDictamenOtro(true);
	    setDescripcionDictamenOtro(true);
	    setRequeridoDictamenOtro(true);
	    setNoAplicaDictamenOtro(true);
	    setEstatusDictamenOtro(true);
	    setJustificacionDictamenOtro(true);
	    setFechaModificacionDictamenOtro(true);
	    setCargadoDictamenOtro(true);
	    setRutaDictamenOtro(true);
	    setIdArchivoDictamenOtro(true);
	    setIdentificadorDictamenOtro(true);
	}

}
