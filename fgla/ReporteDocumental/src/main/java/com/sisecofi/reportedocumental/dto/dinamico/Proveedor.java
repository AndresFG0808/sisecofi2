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
public class Proveedor {

	@NameField(nameField = "nombre_proveedor", order = 35, nombreFront = "Nombre del proveedor")
	private boolean nombreProveedor;
	
	@NameField(nameField = "nombre_comercial", order = 36, nombreFront = "Nombre comercial")
	private boolean nombreComercial;
	
	@NameField(nameField = "s33.nombre", order = 37, fieldComposite = true, nombreFront = "Giro de la empresa")
	private boolean giroEmpresa;
	
	@NameField(nameField = "s34.nombre_contacto", order = 38, fieldComposite = true, nombreFront = "Directorio de contacto")
	private boolean directorioContacto;
	
	@NameField(nameField = "rfc", order = 39, nombreFront = "RFC")
	private boolean rfc;
	
	@NameField(nameField = "s34.representante_legal", order = 40, fieldComposite = true, nombreFront = "Representante legal")
	private boolean representanteLegal;
	
	@NameField(nameField = "s35.numero_titulo", order = 41, fieldComposite = true, nombreFront = "Titulo de servicio")
	private boolean tituloServicio;
	
	@NameField(nameField = "s35.numero_titulo", order = 41, fieldComposite = true, nombreFront = "Titulo de servicio")
	private boolean tituloServicioRequerido;
	
	@NameField(nameField = "s35.vigencia", order = 42, fieldComposite = true, nombreFront = "Vigencia")
	private boolean vigencia;
	
	@NameField(nameField = "s35.vencimiento_titulo", order = 43, fieldComposite = true, nombreFront = "Fecha vencimiento", function = "TO_CHAR(%valor, 'DD/MM/YYYY')")
	private boolean fechaVencimiento;
	
	@NameField(nameField = "s65.resultado", order = 44, fieldComposite = true, nombreFront = "Cumple dictamen")
	private boolean cumpleDictamen;

	@JsonIgnore
	private boolean requerido;
}
