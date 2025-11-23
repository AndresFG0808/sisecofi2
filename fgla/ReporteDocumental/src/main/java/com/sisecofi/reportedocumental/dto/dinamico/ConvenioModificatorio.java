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
public class ConvenioModificatorio {

	@NameField(nameField = "numero_convenio", order = 60, nombreFront = "Número de convenio")
	private boolean numeroConvenio;

	@NameField(nameField = "tipo_convenio", order = 61, nombreFront = "Tipo de convenio")
	private boolean tipoConvenio;

	@NameField(nameField = "fecha_firma", order = 62, nombreFront = "Fecha de firma", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaFirma;

	@NameField(nameField = "fecha_fin_servicio", order = 63, nombreFront = "Fecha fin de servicio", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaFinServicio;

	@NameField(nameField = "fecha_fin", order = 64, nombreFront = "Fecha fin de contrato con CM", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaFinContratoCm;

	@NameField(nameField = "calculo_dias", order = 65, nombreFront = "Cálculo de días naturales")
	private boolean calculoDiasNaturales;

	@NameField(nameField = "incremento", order = 66, nombreFront = "Incremento")
	private boolean incremento;

	@NameField(nameField = "subtotal", order = 67, nombreFront = "Subtotal", function = "SUM(%valor)")
	private boolean subtotal;

	@NameField(nameField = "ieps", order = 68, nombreFront = "IEPS", function = "SUM(%valor)")
	private boolean ieps;

	@NameField(nameField = "s39.porcentaje", fieldComposite = true, order = 69, nombreFront = "IVA")
	private boolean iva;

	@NameField(nameField = "tipo_cambio", order = 70, nombreFront = "Tipo de cambio", function = "SUM(%valor)")
	private boolean tipoCambio;

	@NameField(nameField = "monto_maximo_sin_impuestos", order = 71, nombreFront = "Monto máximo del contrato con CM sin impuestos", function = "SUM(%valor)")
	private boolean montoMaximoContratoCMSin;

	@NameField(nameField = "monto_maximo_con_impuestos", order = 72, nombreFront = "Monto máximo del contrato con CM con impuestos", function = "SUM(%valor)")
	private boolean montoMaximoContratoCMCon;

	@NameField(nameField = "monto_pesos", order = 73, nombreFront = "Monto en pesos", function = "SUM(%valor)")
	private boolean montoPesos;

	@NameField(nameField = "comentarios", order = 74, nombreFront = "Comentarios")
	private boolean comentarios;

	@JsonIgnore
	private boolean requerido;

}
