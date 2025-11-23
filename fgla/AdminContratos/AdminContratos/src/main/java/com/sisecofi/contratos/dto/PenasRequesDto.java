package com.sisecofi.contratos.dto;

import java.util.List;

import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;

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
@EqualsAndHashCode( callSuper = false)
public class PenasRequesDto {
	
	List<PenaContractualContratoModel> penas;
	List<Long> penasEliminadas;
}
