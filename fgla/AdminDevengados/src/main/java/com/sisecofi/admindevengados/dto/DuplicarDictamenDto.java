package com.sisecofi.admindevengados.dto;

import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DuplicarDictamenDto {
	
	private DictamenId dictamenId;
	private dictamenDto dictamenDto;
	private Boolean registrosDictaminados;
	private Boolean penasContractuales;
	private Boolean penasConvencionales; 
	private Boolean deducciones;

}
