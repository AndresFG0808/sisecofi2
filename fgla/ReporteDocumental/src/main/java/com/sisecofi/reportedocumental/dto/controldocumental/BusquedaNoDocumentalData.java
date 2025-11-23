package com.sisecofi.reportedocumental.dto.controldocumental;

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
public class BusquedaNoDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoDocumentalOtro;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoDocumentalOtro;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoDocumentalOtro;

	@NameField(nameField = "'sin fase'", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseDocumentalOtro;

	@NameField(nameField = "'sin plantilla'", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaDocumentalOtro;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionDocumentalOtro;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoDocumentalOtro;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaDocumentalOtro;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusDocumentalOtro;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionDocumentalOtro;

	@NameField(nameField = "s7.fecha_modificacion",fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionDocumentalOtro;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoDocumentalOtro;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaDocumentalOtro;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoDocumentalOtro;
	
	@NameField(nameField = "2", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorDocumentalOtro;

	public BusquedaNoDocumentalData() {
	    inicializarValoresDocumentales();
	}

	private void inicializarValoresDocumentales() {
	    setIdProyectoDocumentalOtro(true);
	    setProyectoDocumentalOtro(true);
	    setNombreCortoDocumentalOtro(true);
	    setFaseDocumentalOtro(true);
	    setPlantillaDocumentalOtro(true);
	    setDescripcionDocumentalOtro(true);
	    setRequeridoDocumentalOtro(true);
	    setNoAplicaDocumentalOtro(true);
	    setEstatusDocumentalOtro(true);
	    setJustificacionDocumentalOtro(true);
	    setFechaModificacionDocumentalOtro(true);
	    setCargadoDocumentalOtro(true);
	    setRutaDocumentalOtro(true);
	    setIdArchivoDocumentalOtro(true);
	    setIdentificadorDocumentalOtro(true);
	}

}
