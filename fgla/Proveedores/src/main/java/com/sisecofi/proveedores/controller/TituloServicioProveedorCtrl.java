package com.sisecofi.proveedores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.proveedores.dto.ConsultaTituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.proveedores.service.TituloServicioProveedorService;
import com.sisecofi.proveedores.util.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class TituloServicioProveedorCtrl {

    private final TituloServicioProveedorService tituloServicioProveedorService;

    @GetMapping("/consultar-servicio-proveedor/{idTituloServicioProveedor}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<ConsultaTituloServicioProveedorDto> obtenerTituloServicioProveedorPorId(
            @PathVariable Long idTituloServicioProveedor) {
        return new ResponseEntity<>(tituloServicioProveedorService
                .obtenerTituloServicioProveedorPorId(idTituloServicioProveedor), HttpStatus.OK);
    }

    @GetMapping("/todos-servicios-proveedor")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<List<ConsultaTituloServicioProveedorDto>> obtenerTodosLosTitulos() {
        return new ResponseEntity<>(tituloServicioProveedorService
                .obtenerTodosLosTitulos(), HttpStatus.OK);
    }

    @PostMapping("/crear-servicio-proveedor")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<TituloServicioResponseDto> crearTituloServicioProveedor(
            @Valid @RequestBody TituloServicioProveedorDto tituloServicioProveedorDto) {
        return new ResponseEntity<>(tituloServicioProveedorService
                .crearTituloServicioProveedor(tituloServicioProveedorDto), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar-servicio-proveedor/{idTituloServicioProveedor}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<TituloServicioResponseDto> actualizarTituloServicioProveedor(
            @PathVariable Long idTituloServicioProveedor,
            @RequestBody TituloServicioProveedorDto tituloServicioProveedorDto) {
        TituloServicioResponseDto tituloProveedorActualizado = tituloServicioProveedorService
                .actualizarTituloServicioProveedor(idTituloServicioProveedor, tituloServicioProveedorDto);
        return ResponseEntity.ok(tituloProveedorActualizado);
    }

    @DeleteMapping("/eliminar-servicio-proveedor/{idTituloServicioProveedor}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<String> eliminarTituloServicioProveedor(@PathVariable Long idTituloServicioProveedor) {
        tituloServicioProveedorService.eliminacionLogicaTituloServicioProveedor(idTituloServicioProveedor);
        return ResponseEntity.ok().build();
    }

}
