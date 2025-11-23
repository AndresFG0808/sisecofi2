package com.sisecofi.admingeneral.dto.adminusuarios;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class BusquedaUsuarioDto {

	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String rfcCorto;
	private String apellidos;
	private Boolean estatus;
	private String actionType;

}
