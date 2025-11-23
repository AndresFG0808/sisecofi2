package com.sisecofi.reportedocumental.dto.dinamico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dictaminado {

	@NameField(nameField = "cantidad_servicio_dictaminado_acumulado", order = 95, nombreFront = "Cantidad de servicios dictaminado")
	private boolean cantidadServicioDictaminado;

	@NameField(nameField = "monto_dictaminado", order = 96, nombreFront = "Monto dictaminado", function = "SUM(%valor)")
	private boolean montoDictaminado;

	@NameField(nameField = "porcentaje_servicios_dictaminados_acumulados", order = 97, nombreFront = "Servicios dictaminados acumulados")
	private boolean servicioEstimadoDictaminado;

	@NameField(nameField = "porcentaje_monto_dictaminados_acumulados", order = 98, nombreFront = "Monto dictaminado acumulado")
	private boolean monotoEstimadoDictaminado;

	/*Pagado*/
	@NameField(nameField = "cantidad_servicio_dictaminado_acumulado", order = 99, nombreFront = "Cantidad de servicios pagados")
	private boolean cantidadServicioPagado;
	
	@NameField(nameField = "monto_dictaminado", order = 100, nombreFront = "Monto pagado", function = "SUM(%valor)")
	private boolean montoPagado;
	
	@NameField(nameField = "porcentaje_servicios_dictaminados_acumulados", order = 102, nombreFront = "Servicios pagados acumulados")	
	private boolean servicioPagadoDictaminado;
	
	@NameField(nameField = "porcentaje_monto_dictaminados_acumulados", order = 102, nombreFront = "Monto pagados acumulado")
	private boolean monotoPagadoDictaminado;
	/*Pagado*/
	
	@JsonIgnore
	private boolean requerido;
}
