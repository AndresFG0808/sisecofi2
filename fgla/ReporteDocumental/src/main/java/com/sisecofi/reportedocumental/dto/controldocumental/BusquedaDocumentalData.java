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
public class BusquedaDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoDocumental;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoDocumental;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoDocumental;

	@NameField(nameField = "s10.nombre", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseDocumental;

	@NameField(nameField = "s4.nombre", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaDocumental;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionDocumental;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoDocumental;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaDocumental;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusDocumental;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionDocumental;

	@NameField(nameField = "s7.fecha_modificacion",fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionDocumental;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoDocumental;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaDocumental;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoDocumental;

	@NameField(nameField = "1", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorDocumental;
	
	@NameField(nameField = "s2.nombre", fieldComposite = true, order = 16, nombreFront = "Fase")
	private boolean estatusProyectoDocumental;
	
	public BusquedaDocumentalData() {
	    inicializarValoresDocumentales();
	}

	private void inicializarValoresDocumentales() {
	    setIdProyectoDocumental(true);
	    setProyectoDocumental(true);
	    setNombreCortoDocumental(true);
	    setFaseDocumental(true);
	    setPlantillaDocumental(true);
	    setDescripcionDocumental(true);
	    setRequeridoDocumental(true);
	    setNoAplicaDocumental(true);
	    setEstatusDocumental(true);
	    setJustificacionDocumental(true);
	    setFechaModificacionDocumental(true);
	    setCargadoDocumental(true);
	    setRutaDocumental(true);
	    setIdArchivoDocumental(true);
	    setIdentificadorDocumental(true);
	    setEstatusProyectoDocumental(true);
	}


}
