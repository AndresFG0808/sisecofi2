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
public class BusquedaDictamenDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoDictamen;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoDictamen;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoDictamen;

	@NameField(nameField = "s10.nombre", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseDictamen;

	@NameField(nameField = "s4.nombre", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaDictamen;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionDictamen;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoDictamen;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaDictamen;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusDictamen;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionDictamen;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionDictamen;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoDictamen;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaDictamen;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoDictamen;

	@NameField(nameField = "15", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorDictamen;

	public BusquedaDictamenDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoDictamen(true);
	    setProyectoDictamen(true);
	    setNombreCortoDictamen(true);
	    setFaseDictamen(true);
	    setPlantillaDictamen(true);
	    setDescripcionDictamen(true);
	    setRequeridoDictamen(true);
	    setNoAplicaDictamen(true);
	    setEstatusDictamen(true);
	    setJustificacionDictamen(true);
	    setFechaModificacionDictamen(true);
	    setCargadoDictamen(true);
	    setRutaDictamen(true);
	    setIdArchivoDictamen(true);
	    setIdentificadorDictamen(true);
	}

}
