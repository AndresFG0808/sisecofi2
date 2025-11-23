package com.sisecofi.libreria.comunes.dto.dictamen;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FacturaDictamenIdContratoDto {
	
	private DictamenId dictamenId;
	
	private Long idContrato;
	
}
