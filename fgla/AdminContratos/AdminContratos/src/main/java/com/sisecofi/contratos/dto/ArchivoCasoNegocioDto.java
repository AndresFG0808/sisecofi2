package com.sisecofi.contratos.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ArchivoCasoNegocioDto {
	@EqualsAndHashCode.Include
	private String archivo;
	private String nombreArchivo;
}
