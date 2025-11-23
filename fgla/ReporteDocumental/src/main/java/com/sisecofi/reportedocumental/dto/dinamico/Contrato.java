package com.sisecofi.reportedocumental.dto.dinamico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Builder
public class Contrato {

	@NameField(nameField = "s36.nombre", order = 45, fieldComposite = true, nombreFront = "Estatus del contrato")
	private boolean estatusContrato;

	@NameField(nameField = "id_contrato", order = 46, nombreFront = "Id")
	private boolean id;

	@NameField(nameField = "nombre_contrato", order = 47, nombreFront = "Nombre del contrato")
	private boolean nombreContrato;

	@NameField(nameField = "s1.nombre_proyecto", fieldComposite = true, order = 48, nombreFront = "Nombre del proyecto")
	private boolean nombreProyecto;

	@NameField(nameField = "s37.numero_contrato", fieldComposite = true, order = 49, nombreFront = "Número de contrato")
	private boolean numeroContrato;

	@NameField(nameField = "s4.nombre_proveedor", fieldComposite = true, order = 50, nombreFront = "Proveedor")
	private boolean provedor;

	@NameField(nameField = "s9.nombre", fieldComposite = true, order = 51, nombreFront = "Tipo de procedimiento")
	private boolean tipoProcedimiento;

	@NameField(nameField = "s38.fecha_inicio_vigencia_contrato", fieldComposite = true, order = 52, nombreFront = "Fecha inicio del contrato", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaInicioContrato;

	@NameField(nameField = "s38.fecha_fin_vigencia_contrato", fieldComposite = true, order = 53, nombreFront = "Fecha término del contrato", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaTerminoContrato;

	@NameField(nameField = "ultimo_cm", order = 54, nombreFront = "Último CM")
	private boolean ultimoCm;

	@NameField(nameField = "s38.monto_maximo_sin_impuestos", fieldComposite = true, order = 55, nombreFront = "Monto máximo", function = "SUM(%valor)")
	private boolean montoMaximo;

	@NameField(nameField = "s38.monto_maximo_sin_impuestos", fieldComposite = true, order = 56, nombreFront = "Monto máximo de último CM", function = "SUM(%valor)")
	private boolean montoMaximoMc;

	@NameField(nameField = "s38.monto_pesos_sin_impuestos", fieldComposite = true, order = 57, nombreFront = "Monto en pesos", function = "SUM(%valor)")
	private boolean montoPesos;

	@NameField(nameField = "s67.administracion", fieldComposite = true, order = 58, nombreFront = "Administración central")
	private boolean administracionCentral;

	@NameField(nameField = "s68.nombre", fieldComposite = true, order = 59, nombreFront = "Administrador del contrato")
	private boolean administradorContrato;

	@JsonIgnore
	private boolean requerido;

}
