package com.sisecofi.proyectos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CatalogoProveedorDto {

	private Long idProveedor;
	private String nombreProveedor;

}
