package com.sisecofi.admindevengados.dto;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancelarDictamenDto {
	
	private DictamenId dictamenId;
	private String descripcion;

}
