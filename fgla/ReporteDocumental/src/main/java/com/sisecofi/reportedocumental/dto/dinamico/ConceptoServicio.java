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
public class ConceptoServicio {

	@NameField(nameField = "id_servicio_contrato", order = 76, nombreFront = "Id")
	private boolean id;

	@NameField(nameField = "s40.grupo", fieldComposite = true, order = 77, nombreFront = "Grupo")
	private boolean grupo;

	@NameField(nameField = "s41.nombre", fieldComposite = true, order = 78, nombreFront = "Tipo de consumo")
	private boolean tipoConsumo;

	@NameField(nameField = "precio_unitario", order = 79, nombreFront = "Precio unitario", function = "SUM(%valor)")
	private boolean precioUnitario;

	@NameField(nameField = "s74.nombre", order = 80, fieldComposite = true, nombreFront = "Tipo de unidad")
	private boolean tipoUnidad;

	@NameField(nameField = "cantidad_minima", order = 81, nombreFront = "Cantidad de servicios mínima")
	private boolean cantidadServicioMinima;

	@NameField(nameField = "cantidad_maxima", order = 82, nombreFront = "Cantidad de servicios máxima")
	private boolean cantidadServicioMaximo;

	@NameField(nameField = "monto_minimo", order = 83, nombreFront = "Monto mínimo", function = "SUM(%valor)")
	private boolean montoMinimo;

	@NameField(nameField = "monto_maximo", order = 84, nombreFront = "Monto máxima", function = "SUM(%valor)")
	private boolean montoMaximo;

	@NameField(nameField = "ieps", order = 85, nombreFront = "Aplica IEPS")
	private boolean ieps;

	@NameField(nameField = "s42.numero_total_servicios", order = 86, fieldComposite = true, nombreFront = "Cantidad de servicios máximos del último CM")
	private boolean cantidadMaximaUltimoCm;

	@NameField(nameField = "s42.monto_maximo_total", order = 87, fieldComposite = true, nombreFront = "Monto máximo del último CM", function = "SUM(%valor)")
	private boolean montoMaximoUltimoCm;

	/* Planeado */
	@NameField(nameField = "s17.cantidad_maxima", fieldComposite = true, order = 88, nombreFront = "Cantidad de servicio planeados")
	private boolean cantidadServicioPlaneado;

	@NameField(nameField = "s17.monto_maximo", fieldComposite = true, order = 89, nombreFront = "Monto planeado", function = "SUM(%valor)")
	private boolean montoPlaneado;
	/* Planeado */

	@JsonIgnore
	private boolean requerido;
}
