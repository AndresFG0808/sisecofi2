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
public class Estimado {

	@NameField(nameField = "cantidad_servicio_estimados", order = 91, nombreFront = "Cantidad de servicios estimado")
	private boolean cantidadServicioEstimado;

	@NameField(nameField = "monto_estimado", order = 92, nombreFront = "Monto estimado", function = "SUM(%valor)")
	private boolean montoEstimado;

	@NameField(nameField = "servicios_acumulados", order = 93, nombreFront = "Servicios estimados acumulados")
	private boolean servicioEstimadoAcumulado;

	@NameField(nameField = "monto_estimado_acumulado", order = 94, nombreFront = "Monto estimado acumulado")
	private boolean monotoEstimadoAcumulado;

	@JsonIgnore
	private boolean requerido;
}
