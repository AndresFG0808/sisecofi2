package com.sisecofi.proveedores.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.proveedores.dto.CatalogoProveedorDto;
import com.sisecofi.proveedores.dto.ConsultaProveedorDto;
import com.sisecofi.proveedores.service.ProveedorService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE_INTERNO)
public class ProveedoresInternoCtrl {

	private final ProveedorService proveedorService;

	@GetMapping("/proveedores-activos")
	@ConsumoInterno
	public ResponseEntity<List<CatalogoProveedorDto>> obtenerTodoslosProveedores() {
		return ResponseEntity.ok(proveedorService.listarProveedoresActivos());
	}

	@GetMapping("/proveedores-buscar-id/{idProveedor}")
	@ConsumoInterno
	public ResponseEntity<ConsultaProveedorDto> buscarProveedorById(@PathVariable("idProveedor") Long idProveedor) {
		return ResponseEntity.ok(proveedorService.obtenerProveedorPorId(idProveedor));
	}
}
