package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import java.util.List;

import com.sisecofi.admindevengados.model.ServicioEstimadoModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicioEstimacionDtoMod {
	
	List<ServicioEstimadoModel> servicios;
	
	private String ultimaModificacion;
	
	private BigDecimal montoEstimado; 

    private BigDecimal montoEstimadoPesos;

}
