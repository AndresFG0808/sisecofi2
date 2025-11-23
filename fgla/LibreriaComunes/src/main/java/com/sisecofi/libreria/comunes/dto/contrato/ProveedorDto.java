package com.sisecofi.libreria.comunes.dto.contrato;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProveedorDto {

	private Long idProveedor;
    private String nombreProveedor;
    
	public ProveedorDto() {
	}

	public ProveedorDto(Long idProveedor, String nombreProveedor) {
		this.idProveedor = idProveedor;
		this.nombreProveedor = nombreProveedor;
	}
    
    
    
    
}
