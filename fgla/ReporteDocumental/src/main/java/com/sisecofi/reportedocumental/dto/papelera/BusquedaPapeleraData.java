package com.sisecofi.reportedocumental.dto.papelera;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author omartinezj
 *
 */

@Data
@Builder
@ToString
@AllArgsConstructor
public class BusquedaPapeleraData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean idProyectoFormateado;	

	@NameField(nameField = "nombre_corto", fieldComposite = true, order = 2, nombreFront = "Nombre corto")
	private boolean nombreCorto;

	@NameField(nameField = "fase", fieldComposite = true, order = 3, nombreFront = "Fase")
	private boolean fase;

	@NameField(nameField = "plantilla", order = 4, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantilla;
	
	@NameField(nameField = "nombre_documento", order = 5, fieldComposite = true, nombreFront = "Nombre del documento")
	private boolean nombreDocumento;

	@NameField(nameField = "descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcion;

	@NameField(nameField = "fecha", fieldComposite = true, order = 7, nombreFront = "Fecha de eliminación", function = "TO_CHAR(%valor, 'DD/MM/YYYY HH24:MI:SS')")
	private boolean fechaEliminacion;

	@NameField(nameField = "usuario_elimina", fieldComposite = true, order = 8, nombreFront = "RFC Usuario")
	private boolean rfcUsuario;

	@NameField(nameField = "tamano", fieldComposite = true, order = 9, nombreFront = "Tamaño")
	private boolean tamano;
	
	@NameField(nameField = "path_nuevo", fieldComposite = true, order = 10, nombreFront = "Ruta Nuevo")
	private boolean ruta;
	
	@NameField(nameField = "id_papelera", fieldComposite = true, order = 11, nombreFront = "Id Papelera")
	private boolean idPapelera;
	
	@NameField(nameField = "id_proyecto", fieldComposite = true, order = 12, nombreFront = "Id Proyecto")
	private boolean idProyecto;
	
	@NameField(nameField = "id_user", fieldComposite = true, order = 13, nombreFront = "Id Usuario")
	private boolean idUsuario;
	
	@NameField(nameField = "path_original", fieldComposite = true, order = 14, nombreFront = "Ruta Original")
	private boolean pathOriginal;
	
	@NameField(nameField = "comentarios", fieldComposite = true, order = 15, nombreFront = "Comentarios")
	private boolean comentarios;
	
	@NameField(nameField = "id_archivo", fieldComposite = true, order = 16, nombreFront = "Id Archivo")
	private boolean idArchivo;
	
	@NameField(nameField = "tipo_archivo", fieldComposite = true, order = 17, nombreFront = "Tipo Archivo")
	private boolean tipoArchivo;
	
	public BusquedaPapeleraData() {
	    initialize();
	}

	private void initialize() {
	    setIdProyectoFormateado(true);
	    setNombreCorto(true);
	    setFase(true);
	    setPlantilla(true);
	    setNombreDocumento(true);
	    setDescripcion(true);
	    setFechaEliminacion(true);
	    setRfcUsuario(true);
	    setTamano(true);
	    setRuta(true);
	    setIdPapelera(true);
	    setIdProyecto(true);
	    setIdUsuario(true);
	    setPathOriginal(true);
	    setComentarios(true);
	    setIdArchivo(true);
	    setTipoArchivo(true);
	}

	
}
