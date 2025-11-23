package com.sisecofi.admindevengados.dto;

import java.util.List;

import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActualizarEstimacionDto {
	
	private EstimacionDto estimaciones;
	private Long idContrato;
	private DictamenId dictamenId;
	private List<Integer> posiciones;

}
