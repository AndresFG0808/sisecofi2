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
public class PenalizacionDeduccion {

	@NameField(nameField = "s69.nombre", fieldComposite = true, order = 134, nombreFront = "Tipo")
	private boolean tipo;

	@NameField(nameField = "s72.nombre", fieldComposite = true, order = 135, nombreFront = "Tipo informe")
	private boolean tipoInforme;

	private boolean documento;

	@NameField(nameField = "pena_aplicable", order = 137, nombreFront = "Descripción")
	private boolean descripcion;

	@NameField(nameField = "s70.nombre", fieldComposite = true, order = 138, nombreFront = "Desglose")
	private boolean desglose;

	@NameField(nameField = "concepto_servicio", order = 139, nombreFront = "Concepto de servicio")
	private boolean conceptoServicio;

	@NameField(nameField = "monto", order = 140, nombreFront = "Monto", function = "SUM(%valor)")
	private boolean monto;

	@NameField(nameField = "s49.periodo_inicio", order = 141, fieldComposite = true, nombreFront = "Periodo de inicio")
	private boolean periodoInicio;

	@NameField(nameField = "s49.periodo_fin", order = 142, fieldComposite = true, nombreFront = "Periodo termino")
	private boolean periodoTermino;

	@NameField(nameField = "s73.nombre", order = 143, fieldComposite = true, nombreFront = "Periodo control")
	private boolean periodoControl;

	@JsonIgnore
	private boolean requerido;
}
