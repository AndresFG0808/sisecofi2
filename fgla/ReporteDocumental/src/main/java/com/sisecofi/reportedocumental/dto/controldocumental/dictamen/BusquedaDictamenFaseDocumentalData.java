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
public class BusquedaDictamenFaseDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoDictamenFase;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoDictamenFase;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoDictamenFase;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseDictamenFase;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaDictamenFase;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionDictamenFase;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoDictamenFase;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaDictamenFase;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusDictamenFase;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionDictamenFase;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionDictamenFase;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoDictamenFase;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaDictamenFase;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoDictamenFase;

	@NameField(nameField = "16", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorDictamenFase;

	public BusquedaDictamenFaseDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoDictamenFase(true);
	    setProyectoDictamenFase(true);
	    setNombreCortoDictamenFase(true);
	    setFaseDictamenFase(true);
	    setPlantillaDictamenFase(true);
	    setDescripcionDictamenFase(true);
	    setRequeridoDictamenFase(true);
	    setNoAplicaDictamenFase(true);
	    setEstatusDictamenFase(true);
	    setJustificacionDictamenFase(true);
	    setFechaModificacionDictamenFase(true);
	    setCargadoDictamenFase(true);
	    setRutaDictamenFase(true);
	    setIdArchivoDictamenFase(true);
	    setIdentificadorDictamenFase(true);
	}

}
