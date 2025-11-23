package com.sisecofi.proyectos.dto.cierre;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ObtenerProyectodto {
	
	private String nombreCorto;
	private Long idProyecto;
	private String idProyectoAGP;
	private String nombreCompleto;
	private String lider;
	private String porcentajePlaneado;
	private String porcentajeReal;

}
