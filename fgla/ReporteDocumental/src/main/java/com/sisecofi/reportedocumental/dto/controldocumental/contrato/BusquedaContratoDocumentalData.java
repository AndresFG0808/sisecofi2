package com.sisecofi.reportedocumental.dto.controldocumental.contrato;

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
public class BusquedaContratoDocumentalData {

	@NameField(nameField = "id_proyecto", order = 1, nombreFront = "Id proyecto")
	private boolean idProyectoContrato;

	@NameField(nameField = "id_proyecto", order = 2, nombreFront = "Id proyecto", function = "LPAD(CAST(%valor AS TEXT), 5, '0')")
	private boolean proyectoContrato;

	@NameField(nameField = "nombre_corto", order = 3, nombreFront = "Nombre corto")
	private boolean nombreCortoContrato;

	@NameField(nameField = "s10.nombre", fieldComposite = true, order = 4, nombreFront = "Fase")
	private boolean faseContrato;

	@NameField(nameField = "s4.nombre", order = 5, fieldComposite = true, nombreFront = "Plantilla")
	private boolean plantillaContrato;

	@NameField(nameField = "s7.descripcion", fieldComposite = true, order = 6, nombreFront = "Descripción")
	private boolean descripcionContrato;

	@NameField(nameField = "s7.obligatorio", fieldComposite = true, order = 7, nombreFront = "Requerido")
	private boolean requeridoContrato;

	@NameField(nameField = "s7.no_aplica", fieldComposite = true, order = 8, nombreFront = "No aplica")
	private boolean noAplicaContrato;

	@NameField(nameField = "s7.estatus", fieldComposite = true, order = 9, nombreFront = "Estatus")
	private boolean estatusContrato;

	@NameField(nameField = "s7.justificacion", fieldComposite = true, order = 10, nombreFront = "Justificación")
	private boolean justificacionContrato;

	@NameField(nameField = "s7.fecha_modificacion", fieldComposite = true, order = 11, nombreFront = "Fecha última modificación", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaModificacionContrato;

	@NameField(nameField = "s7.cargado", fieldComposite = true, order = 12, nombreFront = "Cargado")
	private boolean cargadoContrato;

	@NameField(nameField = "s7.ruta", fieldComposite = true, order = 13, nombreFront = "Ruta")
	private boolean rutaContrato;

	@NameField(nameField = "s7.id", fieldComposite = true, order = 14, nombreFront = "Id")
	private boolean idArchivoContrato;

	@NameField(nameField = "6", fieldComposite = true, order = 15, nombreFront = "identificador")
	private boolean identificadorContrato;

	public BusquedaContratoDocumentalData() {
	    inicializarCampos();
	}

	private void inicializarCampos() {
	    setIdProyectoContrato(true);
	    setProyectoContrato(true);
	    setNombreCortoContrato(true);
	    setFaseContrato(true);
	    setPlantillaContrato(true);
	    setDescripcionContrato(true);
	    setRequeridoContrato(true);
	    setNoAplicaContrato(true);
	    setEstatusContrato(true);
	    setJustificacionContrato(true);
	    setFechaModificacionContrato(true);
	    setCargadoContrato(true);
	    setRutaContrato(true);
	    setIdArchivoContrato(true);
	    setIdentificadorContrato(true);
	}

}
