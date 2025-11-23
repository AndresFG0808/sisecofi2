package com.sisecofi.libreria.comunes.dto.proyecto;



import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiderDto {

	private Long idHistorico;
	private Long idFichaTecnica;
	private String nombre;
	private String puesto;
	private String correo;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private boolean estatus;
	private boolean estatusHistorico;
	private Integer nivel;
	private Integer idReferencia;
	private UsuarioInfoDto usuario;
	
	public String getNombre() {
		if (usuario!=null) {
			return usuario.getNombre();
		}else return nombre;
	}
	
	public String getCorreo() {
		if (usuario!=null) {
			return usuario.getCorreo();
		}else return correo;
	}
	
}
