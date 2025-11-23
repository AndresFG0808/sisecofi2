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
public class Dictamen {

	@NameField(nameField = "id_dictamen", order = 116, nombreFront = "Id")
	private boolean id;

	@NameField(nameField = "s2.nombre_corto", fieldComposite = true, order = 117, nombreFront = "Nombre corto del contrato")
	private boolean nombreCortoContrato;

	@NameField(nameField = "s2.numero_contrato", fieldComposite = true, order = 118, nombreFront = "Número de contrato")
	private boolean numeroContrato;

	@NameField(nameField = "s54.nombre_proveedor", order = 119, fieldComposite = true, nombreFront = "Proveedor")
	private boolean proveedor;

	@NameField(nameField = "estatus", order = 120, nombreFront = "Estatus")
	private boolean estatus;

	@NameField(nameField = "periodo_inicio", order = 121, nombreFront = "Periodo de inicio")
	private boolean periodoInicio;

	@NameField(nameField = "periodo_fin", order = 122, nombreFront = "Periodo fin")
	private boolean periodoFin;

	@NameField(nameField = "s50.nombre", order = 123, fieldComposite = true, nombreFront = "Periodo de control")
	private boolean periodoControl;

	@NameField(nameField = "s51.porcentaje", order = 124, fieldComposite = true, nombreFront = "IVA")
	private boolean iva;

	@NameField(nameField = "tipo_cambio_referencial", order = 125, nombreFront = "Tipo de cambio referencial")
	private boolean tipoCambioReferencial;

	@NameField(nameField = "descripcion", order = 126, nombreFront = "Descripcion")
	private boolean descripcion;

	@NameField(nameField = "s71.nombre", order = 127, fieldComposite = true, nombreFront = "Fase")
	private boolean fase;

	@NameField(nameField = "s53.sub_total", order = 128, fieldComposite = true, nombreFront = "Subtotal", function = "SUM(%valor)")
	private boolean subTotal;

	@NameField(nameField = "s53.deducciones", order = 129, fieldComposite = true, nombreFront = "Deducciones")
	private boolean deducciones;

	@NameField(nameField = "s53.ieps", order = 130, fieldComposite = true, nombreFront = "IEPS", function = "SUM(%valor)")
	private boolean ieps;

	@NameField(nameField = "s53.otros_impuestos", order = 131, fieldComposite = true, nombreFront = "Otros impuestos", function = "SUM(%valor)")
	private boolean otrosImpuestos;

	@NameField(nameField = "s53.total", order = 132, fieldComposite = true, nombreFront = "Total", function = "SUM(%valor)")
	private boolean total;

	@NameField(nameField = "s53.total_pesos", order = 133, fieldComposite = true, nombreFront = "Total en pesos", function = "SUM(%valor)")
	private boolean totalpesos;

	@JsonIgnore
	private boolean requerido;
}
