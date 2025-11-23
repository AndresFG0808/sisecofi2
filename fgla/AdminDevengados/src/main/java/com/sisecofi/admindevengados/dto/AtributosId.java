package com.sisecofi.admindevengados.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtributosId {
    private String  nombreCorto;
    private Long idProveedor;
    private Integer consecutivo;
    
	public AtributosId(String nombreCorto, Long idProveedor, Integer consecutivo) {
		this.nombreCorto = nombreCorto;
		this.idProveedor = idProveedor;
		this.consecutivo = consecutivo;
	}
    
	public AtributosId() {
	
	}
    
}
