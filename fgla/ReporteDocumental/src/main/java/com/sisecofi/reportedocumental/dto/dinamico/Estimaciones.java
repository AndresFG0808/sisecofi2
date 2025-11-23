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
public class Estimaciones {

	@NameField(nameField = "id_estimacion", order = 103, nombreFront = "Id")
	private boolean id;

	@NameField(nameField = "s2.nombre_corto", fieldComposite = true, order = 104, nombreFront = "Nombre corto del contrato")
	private boolean nombreCortoContrato;

	@NameField(nameField = "s2.numero_contrato", fieldComposite = true, order = 105, nombreFront = "Número de contrato")
	private boolean numeroContrato;

	@NameField(nameField = "s46.nombre_proveedor", fieldComposite = true, order = 106, nombreFront = "Proveedor")
	private boolean proveedor;

	@NameField(nameField = "estatus", order = 107, nombreFront = "Estatus")
	private boolean estatus;

	@NameField(nameField = "periodo_inicio", order = 108, nombreFront = "Periodo de inicio")
	private boolean periodoInicio;

	@NameField(nameField = "periodo_fin", order = 109, nombreFront = "Peridodo fin")
	private boolean periodoFin;

	@NameField(nameField = "s47.nombre", fieldComposite = true, order = 110, nombreFront = "Periodo de control")
	private boolean periodoControl;

	@NameField(nameField = "s48.porcentaje", fieldComposite = true, order = 111, nombreFront = "IVA")
	private boolean iva;

	@NameField(nameField = "tipo_cambio_referencial", order = 112, nombreFront = "Tipo de cambio referencial", function = "SUM(%valor)")
	private boolean tipoCambioReferencial;

	@NameField(nameField = "monto_estimado", order = 113, nombreFront = "Monto estimando total", function = "SUM(%valor)")
	private boolean montoEstimadoTotal;

	@NameField(nameField = "monto_estimado_pesos", order = 114, nombreFront = "Monto estimado total en pesos", function = "SUM(%valor)")
	private boolean montoEstimadoTotalPesos;

	@NameField(nameField = "justificacion", order = 115, nombreFront = "Justificación")
	private boolean justificacion;

	@JsonIgnore
	private boolean requerido;
}
