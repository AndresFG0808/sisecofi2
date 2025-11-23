package com.sisecofi.reportedocumental.dto.dinamico;

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
public class Planeado {

	@NameField(nameField = "s17.cantidad_maxima", fieldComposite = true, order = 87, nombreFront = "Cantidad de servicio planeados")
	private boolean cantidadServicioPlaneado;

	@NameField(nameField = "s17.monto_maximo", fieldComposite = true, order = 88, nombreFront = "Monto planeado")
	private boolean montoPlaneado;

	private boolean requerido;
}
