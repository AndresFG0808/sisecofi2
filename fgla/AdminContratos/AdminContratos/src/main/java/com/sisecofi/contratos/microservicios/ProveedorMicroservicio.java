package com.sisecofi.contratos.microservicios;

import com.sisecofi.contratos.dto.CatalogoProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "proveedorholder", url = "${url.proveedores}")
public interface ProveedorMicroservicio {

    @GetMapping("/proveedores-activos")
    List<CatalogoProveedorDto> obtenerTodoslosProveedores();

    @GetMapping("/proveedores-buscar-id/{idProveedor}")
    ProveedorModel buscarProveedor(@PathVariable("idProveedor") Long idProveedor);
}
