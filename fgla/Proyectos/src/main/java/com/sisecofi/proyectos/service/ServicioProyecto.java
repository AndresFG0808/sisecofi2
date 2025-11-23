package com.sisecofi.proyectos.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.proyectos.dto.ProyectoDtoLigero;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoMetaDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;


public interface ServicioProyecto {

	Page<ProyectoMetaDto> buscarProyectos(EstructuraProyectoMetaDto proyecto); //
	
	List<ProyectoDtoLigero> buscarProyectosLista(EstructuraProyectoMetaDto proyecto); //

	ProyectoResponse crearProyecto(ProyectoModel proyecto); //

	ProyectoResponse actualizarProyecto(ProyectoModel proyecto, Long idProyecto); //
	
	ProyectoResponse actualizarEstatus(CatEstatusProyecto estatus, Long idProyecto); //

	String obtnerUltimoId(); //

	ProyectoResponse obtenerProyecto(Long idProyecto); //

	List<ProyectoNombreDto> obtenerNombresCortos(Integer idEstatus); //
	
	boolean eliminarProyecto(Long idProyecto); //

	List<ProyectoSimpleDto> obtenerProyectosLista();

	ProyectoModel obtenerProyectoModel(Long idProyecto);

	Integer obtenerIdPorNombreCorto(String nombreCorto);

	List<ProyectoModel> obtenerProyectosEstatus(Integer idEstatus);

	Page<ProyectoDtoLigero> buscarProyectosModel(EstructuraProyectoMetaDto proyecto);

	String obtenerCriterios(EstructuraProyectoMetaDto proyecto);

	void actualizarUltimaModificacion(Long idProyecto);

	String obtenerUltimaMod(Long idProyecto);

	List<ProyectoSimpleDto> obtenerProyectosListaCompleto();
	
	Boolean verificarCancelado (Long idProyecto);
}
