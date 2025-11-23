package com.sisecofi.admindevengados.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SoporteDocumentalDto {

	private Long idSoporteDocumento;
	
	private String nombrePenasDeducciones;

	private String numeroOficio;

	private String fechaSolicitudDictamen;

	private String fechaRecepcionDictamen;

	private Long idDictamen;

}
