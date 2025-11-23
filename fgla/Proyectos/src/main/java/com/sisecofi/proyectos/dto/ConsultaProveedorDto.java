package com.sisecofi.proyectos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ConsultaProveedorDto {

	private Long idProveedor;
	private String nombreProveedor;
	private String nombreComercial;
	private String rfc;
	private String direccion;
	private String comentarios;
	private Boolean estatus;
	private String idAgs;

}
