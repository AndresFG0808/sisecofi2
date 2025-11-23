package com.sisecofi.admindevengados.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TipoDto {
	
	private LocalDateTime fechaCreacion;

	private LocalDateTime fechaModificacion;

	private boolean estatus;

	private Integer idDeduccion;
	
	private String descripcion;
	
	private String nombre;

}
