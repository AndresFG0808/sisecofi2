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
public class NotaCredito {

	@NameField(nameField = "folio", order = 167, nombreFront = "Folio")
	private boolean folio;

	@NameField(nameField = "comprobante_fiscal", order = 168, nombreFront = "Comprobante fiscal")
	private boolean comprobanteFiscal;

	@NameField(nameField = "fecha_generacion", order = 169, nombreFront = "Fecha de generación")
	private boolean fechaGeneracion;

	@NameField(nameField = "estatus", order = 170, nombreFront = "Estatus")
	private boolean estatus;

	@NameField(nameField = "s61.nombre", fieldComposite = true, order = 171, nombreFront = "Moneda")
	private boolean moneda;

	@NameField(nameField = "s62.porcentaje", fieldComposite = true, order = 172, nombreFront = "Tasa")
	private boolean tasa;

	@NameField(nameField = "sub_total", order = 173, nombreFront = "Subtotal", function = "SUM(%valor)")
	private boolean subTotal;

	@NameField(nameField = "ieps", order = 174, nombreFront = "IEPS", function = "SUM(%valor)")
	private boolean ieps;

	@NameField(nameField = "iva", order = 175, nombreFront = "IVA", function = "SUM(%valor)")
	private boolean iva;

	@NameField(nameField = "otros_impuestos", order = 176, nombreFront = "Otros impuestos", function = "SUM(%valor)")
	private boolean otrosImpuestos;

	@NameField(nameField = "total", order = 177, nombreFront = "Total", function = "SUM(%valor)")
	private boolean total;

	@NameField(nameField = "total_pesos", order = 178, nombreFront = "Total en pesos", function = "SUM(%valor)")
	private boolean totalPesos;

	@NameField(nameField = "comentarios", order = 179, nombreFront = "Comentarios")
	private boolean comentarios;

	// desglose de montos
	@NameField(nameField = "porcentaje_sat", order = 180, nombreFront = "% SAT")
	private boolean sat;

	@NameField(nameField = "monto_sat", order = 181, nombreFront = "Monto")
	private boolean monto;

	@NameField(nameField = "monto_pesos_sat", order = 182, nombreFront = "Monto en pesos")
	private boolean montoPesos;

	@NameField(nameField = "porcentajecc", order = 183, nombreFront = "% Convenio de coloboración")
	private boolean convenioColaboracion;

	@NameField(nameField = "montocc", order = 184, nombreFront = "Monto")
	private boolean montoConveio;

	@NameField(nameField = "monto_pesoscc", order = 185, nombreFront = "Monto en pesos")
	private boolean montoPesosConvenio;
	// desglose de montos
	
	@JsonIgnore
	private boolean requerido;
}
