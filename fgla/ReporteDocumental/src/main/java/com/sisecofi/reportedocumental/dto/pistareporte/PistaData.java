package com.sisecofi.reportedocumental.dto.pistareporte;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class PistaData {

	@NameField(nameField = "id_pista", order = 1, nombreFront = "Id")
	private boolean idPista;

	@NameField(nameField = "s2.nombre", fieldComposite = true, order = 2, nombreFront = "Módulo")
	private boolean modulo;

	@NameField(nameField = "s3.nombre", fieldComposite = true, order = 3, nombreFront = "Sección")
	private boolean seccion;

	@NameField(nameField = "fecha_movimiento", order = 4, nombreFront = "Fecha y hora", function = "TO_CHAR(%valor, 'DD/MM/YYYY HH24:MI:SS')")
	private boolean fechaHora;

	@NameField(nameField = "s5.nombre", fieldComposite = true, order = 5, nombreFront = "Empleado SAT")
	private boolean empleadoSat;

	@NameField(nameField = "s5.rfc_largo", fieldComposite = true, order = 6, nombreFront = "RFC")
	private boolean rfc;

	@NameField(nameField = "s4.descripcion", fieldComposite = true, order = 7, nombreFront = "Tipo de movimiento")
	private boolean tipoMovimiento;

	public PistaData() {
	    initializeFields();
	}

	private void initializeFields() {
	    setIdPista(true);
	    setEmpleadoSat(true);
	    setFechaHora(true);
	    setModulo(true);
	    setRfc(true);
	    setSeccion(true);
	    setTipoMovimiento(true);
	}

}
