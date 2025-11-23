package com.sisecofi.libreria.comunes.dto.contrato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInfoDto {
	private String idUsuarioStr;
    private Integer idUsuario;
    private String nombre;
    private String telefono;
    private String puesto;
    private String correo = "";
    private Integer nivel;
}
