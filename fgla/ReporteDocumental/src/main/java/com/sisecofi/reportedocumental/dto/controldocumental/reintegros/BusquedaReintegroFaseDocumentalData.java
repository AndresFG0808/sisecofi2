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
public class BusquedaReintegroFaseDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoReintegroFase;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoReintegroFase;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoReintegroFase;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseReintegroFase;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaReintegroFase;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionReintegroFase;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoReintegroFase;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaReintegroFase;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusReintegroFase;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionReintegroFase;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionReintegroFase;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoReintegroFase;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaReintegroFase;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoReintegroFase;

	@NameField(nameField = "13", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorReintegroFase;

	public BusquedaReintegroFaseDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoReintegroFase(true);
	    setProyectoReintegroFase(true);
	    setNombreCortoReintegroFase(true);
	    setFaseReintegroFase(true);
	    setPlantillaReintegroFase(true);
	    setDescripcionReintegroFase(true);
	    setRequeridoReintegroFase(true);
	    setNoAplicaReintegroFase(true);
	    setEstatusReintegroFase(true);
	    setJustificacionReintegroFase(true);
	    setFechaModificacionReintegroFase(true);
	    setCargadoReintegroFase(true);
	    setRutaReintegroFase(true);
	    setIdArchivoReintegroFase(true);
	    setIdentificadorReintegroFase(true);
	}

}
