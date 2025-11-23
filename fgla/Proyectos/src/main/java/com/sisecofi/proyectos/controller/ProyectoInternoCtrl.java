package com.sisecofi.proyectos.controller;

import java.util.List;
import java.util.Set;

import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.service.ServicioProyecto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.proyectos.service.ServicioAsociacion;
import com.sisecofi.proyectos.service.ServicioGestionDocumental;
import com.sisecofi.proyectos.util.Constantes;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ProyectoInternoCtrl {

	private final ServicioAsociacion servicioAsociacion;
	private final ServicioProyecto proyecto;
	private final ServicioGestionDocumental servicioGestionDocumental;

	@GetMapping("/"+Constantes.PATH_BASE_INTERNO + "/plantillasOcupadas")
	@ConsumoInterno
	public ResponseEntity<Set<Integer>> plantillasOcupadas(){
		return new ResponseEntity<>(servicioAsociacion.plantillasOcupadas(), org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/"+Constantes.PATH_BASE_INTERNO +"/proyecto/{idProyecto}")
	@ConsumoInterno
	public ResponseEntity<ProyectoResponse> obtenerProyecto(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(proyecto.obtenerProyecto(idProyecto), org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/"+Constantes.PATH_BASE_INTERNO+ "/proyectos")
	@ConsumoInterno
	public ResponseEntity<List<ProyectoSimpleDto>> obtenerProyectos() {
		return new ResponseEntity<>(proyecto.obtenerProyectosLista(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/"+Constantes.PATH_BASE_INTERNO+ "/proyectos-completos")
	@ConsumoInterno
	public ResponseEntity<List<ProyectoSimpleDto>> obtenerProyectosCompletos() {
		return new ResponseEntity<>(proyecto.obtenerProyectosListaCompleto(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/"+Constantes.PATH_BASE_INTERNO+"/proyecto-model/{idProyecto}")
	@ConsumoInterno
	public ResponseEntity<ProyectoModel> obtenerProyectoModel(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(proyecto.obtenerProyectoModel(idProyecto), org.springframework.http.HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/"+Constantes.PATH_BASE_INTERNO+"/gestion-documental-proyectos/{idProyecto}")
	@ConsumoInterno
	public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumental(@PathVariable Long idProyecto) {
		return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumental(idProyecto),
				org.springframework.http.HttpStatus.OK);
		}


}
