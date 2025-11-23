package com.sisecofi.contratos.dto;

import java.math.BigDecimal;
import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CasoNegocioDto {
	@EqualsAndHashCode.Include
	List<ServicioContratoModel> listaServicio;
	BigDecimal [][] volumeria;
	boolean exportar;
}
