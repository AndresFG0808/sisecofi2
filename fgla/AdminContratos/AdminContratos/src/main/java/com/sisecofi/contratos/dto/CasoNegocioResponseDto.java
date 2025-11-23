package com.sisecofi.contratos.dto;


import java.util.List;
import java.util.Map;

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
public class CasoNegocioResponseDto {
	@EqualsAndHashCode.Include
	Map<Integer, List<String>> mapa;
	boolean cargado;
}
