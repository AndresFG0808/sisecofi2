package com.sisecofi.libreria.comunes.dto.dictamen;


import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DictamenRequest {
	
	@Valid
	private dictamenDto dictamendto;
	private DictamenId dictamenId;

}
