package com.sisecofi.admindevengados.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode( callSuper = false)
public class DevengadoRequest {

	private long idContrato;
	private String tipoConsumo;
	private Integer idEstatus;
	private Long idProveedor;
	private String contratoVigente;

}
