package com.sisecofi.proyectos.microservicio;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sisecofi.proyectos.dto.CatalogoProveedorDto;
import com.sisecofi.proyectos.dto.ConsultaProveedorDto;

@FeignClient(value = "proveedorholder", url = "${url.proveedores}")
public interface ProveedorMicroservicio {

	@GetMapping("/proveedores-activos")
	List<CatalogoProveedorDto> obtenerTodoslosProveedores();

	@GetMapping("/proveedores-buscar-id/{idProveedor}")
	ConsultaProveedorDto buscarProveedorById(@PathVariable("idProveedor") Long idProveedor);
}