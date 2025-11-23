package com.sisecofi.contratos.dto;

import java.util.List;
import java.util.Set;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;

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
@EqualsAndHashCode(callSuper = false)
public class ServiciosConvenioDto {
	
	private Set <ServicioConvenioModel> servicios;
	
	private List<String> tipoConvenio;
}
