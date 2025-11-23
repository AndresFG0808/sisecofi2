package com.sisecofi.admindevengados.dto.estimacion;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode( callSuper = false)
public class EstimacionProyeccionDto {
    private BigDecimal cantidadServiciosEstimados;
    private BigDecimal serviciosAcumulados;
    
	public EstimacionProyeccionDto(BigDecimal cantidadServiciosEstimados, BigDecimal serviciosAcumulados) {
		this.cantidadServiciosEstimados = cantidadServiciosEstimados;
		this.serviciosAcumulados = serviciosAcumulados;
	}

	public EstimacionProyeccionDto() {
	}
	
	
    
    
}
