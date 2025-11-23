package com.sisecofi.contratos.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargaLayoutInformesDto {
	private String tipoLayout;
	private Long idContrato;
	private MultipartFile archivo;
	
}
