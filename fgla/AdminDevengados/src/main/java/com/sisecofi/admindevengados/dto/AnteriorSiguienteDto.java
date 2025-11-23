package com.sisecofi.admindevengados.dto;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnteriorSiguienteDto {
	
	private DictamenId dictamenId;
	private Long idContrato;
	private Long idProveedor;

}
