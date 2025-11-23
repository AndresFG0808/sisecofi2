package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MontoPesosResumenDto {
	
	private DictamenId idDictamen;	
	private BigDecimal montoPesos;


}
