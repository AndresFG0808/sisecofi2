package com.sisecofi.contratos.dto;

import java.util.List;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;

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
public class CasoNegocioConvenioDto {
	@EqualsAndHashCode.Include
	List<ServicioConvenioModel> listaServicio;
	int [][] volumeria;
}
