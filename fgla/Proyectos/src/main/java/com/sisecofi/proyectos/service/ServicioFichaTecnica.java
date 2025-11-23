package com.sisecofi.proyectos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.FichaRequest;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;

public interface ServicioFichaTecnica {

	FichaTecnicaResponse obtenerFicha(Long id);
	
	boolean agregarLider(Long id);

	FichaTecnicaResponse guardarFicha(FichaRequest fichaResponse, Long idProyecto, boolean completo);
	
	List<Usuario> enlistarUsuarios();

	List<UsuarioInfoDto> obtenerTodosLosUsuarios();
}
