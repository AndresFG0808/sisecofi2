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
public class Facturas {

	@NameField(nameField = "folio", order = 144, nombreFront = "Folio")
	private boolean folioFactura;

	@NameField(nameField = "comprobante_fiscal", order = 145, nombreFront = "Comprobante fiscal")
	private boolean comprobanteFiscalFactura;

	@NameField(nameField = "fecha_facturacion", order = 146, nombreFront = "Fecha de facturación")
	private boolean fechaFacturacionFactura;

	@NameField(nameField = "estatus", order = 147, nombreFront = "Estatus")
	private boolean estatusFactura;

	@NameField(nameField = "s57.nombre", fieldComposite = true, order = 148, nombreFront = "Moneda")
	private boolean monedaFactura;

	@NameField(nameField = "s58.porcentaje", fieldComposite = true, order = 149, nombreFront = "Tasa")
	private boolean tasaFactura;

	@NameField(nameField = "sub_total", order = 150, nombreFront = "Subtotal", function = "SUM(%valor)")
	private boolean subTotalFactura;

	@NameField(nameField = "ieps", order = 151, nombreFront = "IEPS", function = "SUM(%valor)")
	private boolean iepsFactura;

	@NameField(nameField = "iva", order = 151, nombreFront = "IVA", function = "SUM(%valor)")
	private boolean ivaFactura;

	@NameField(nameField = "otros_impuestos", order = 153, nombreFront = "Otros impuestos", function = "SUM(%valor)")
	private boolean otrosImpuestosFactura;

	@NameField(nameField = "total", order = 154, nombreFront = "Total facturado", function = "SUM(%valor)")
	private boolean totalFacturadoFactura;

	@NameField(nameField = "total_pesos", order = 155, nombreFront = "Total facturado en pesos", function = "SUM(%valor)")
	private boolean totalFacturadoPesosFactura;

	@NameField(nameField = "monto_pesos_sat", order = 156, nombreFront = "Total pagado en pesos", function = "SUM(%valor)")
	private boolean totalPagadoPesosFactura;

	@NameField(nameField = "comentarios", order = 157, nombreFront = "Comentarios")
	private boolean comentariosFactura;

	// desglose de montos
	@NameField(nameField = "porcentaje_sat", order = 158, nombreFront = "% SAT")
	private boolean satFactura;

	@NameField(nameField = "monto_sat", order = 159, nombreFront = "Monto")
	private boolean montoFactura;

	@NameField(nameField = "monto_pesos_sat", order = 160, nombreFront = "Monto en pesos")
	private boolean montoPesosFactura;

	@NameField(nameField = "porcentajecc", order = 161, nombreFront = "% Convenio de colaboración")
	private boolean convenioColaboracionFactura;

	@NameField(nameField = "montocc", order = 162, nombreFront = "Monto")
	private boolean montoConveioFactura;

	@NameField(nameField = "monto_pesoscc", order = 163, nombreFront = "Monto en pesos")
	private boolean montoPesosConvenioFactura;

	@NameField(nameField = "s63.folio_ficha_pago", order = 164, fieldComposite = true, nombreFront = "FichaNAFIN")
	private boolean fichaNaFinFactura;

	@NameField(nameField = "s63.fecha_notificacion", order = 165, fieldComposite = true, nombreFront = "Fecha NAFIN")
	private boolean fechaNaFinFactura;

	@NameField(nameField = "s63.tipo_cambio_pagado", order = 166, fieldComposite = true, nombreFront = "Tipo de cambio NAFIN")
	private boolean tipoCambioNaFinFactura;
	// desglose de montos

	@JsonIgnore
	private boolean requerido;
}
