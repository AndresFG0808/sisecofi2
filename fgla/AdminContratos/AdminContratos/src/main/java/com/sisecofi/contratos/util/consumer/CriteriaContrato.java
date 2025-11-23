package com.sisecofi.contratos.util.consumer;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriteriaContrato {
	
	private Long idContrato;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
    private Integer idAdministracionCentral;
    private Integer idEstatusContrato;
    private Long idProveedor;
    private boolean estatus;
    private Usuario user;
    private boolean filtroUsuario;

}
