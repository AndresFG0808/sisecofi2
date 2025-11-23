package com.sisecofi.proveedores.controller;

import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


import com.sisecofi.proveedores.dto.DirectorioProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.proveedores.service.DirectorioProveedorService;
import com.sisecofi.proveedores.util.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author adtolentino
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DirectorioProveedorCtrl {

    @Autowired
    private DirectorioProveedorService directorioProveedorService;

    //Crear directorio de proveedor
    @PostMapping("/crear-directorio-contacto")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
	"@seguridad.validarRolAdminSistemaSecundario() || " +
	"@seguridad.validarRolApoyoAcppi() || "  +
	"@seguridad.validarRolGestorTitutulos() || " +
	"@seguridad.validarRolLiderProyecto()")
    public ResponseEntity<DirectorioProveedorModel> crearDirectorioProveedor(
            @Valid @RequestBody DirectorioProveedorDto directorio) {
        return new ResponseEntity<>(directorioProveedorService
                .crearDirectorioProveedor(directorio), HttpStatus.CREATED);
    }

    @GetMapping("/tabla-directorios")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
	"@seguridad.validarRolAdminSistemaSecundario() || " +
	"@seguridad.validarRolApoyoAcppi() || "  +
	"@seguridad.validarRolGestorTitutulos() || " +
	"@seguridad.validarRolLiderProyecto()")
    public ResponseEntity<List<DirectorioProveedorDto>> obtenerDirectorioContacto (){
        return new ResponseEntity<>(directorioProveedorService.obtenerDirectorioProveedor(), HttpStatus.OK);
    }

    

    @GetMapping("/directorio/{id}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
	"@seguridad.validarRolAdminSistemaSecundario() || " +
	"@seguridad.validarRolApoyoAcppi() || "  +
	"@seguridad.validarRolGestorTitutulos() || " +
	"@seguridad.validarRolLiderProyecto()")
    public ResponseEntity<DirectorioProveedorDto> obtenerDirectorioPorId(@PathVariable Long id){
        return new ResponseEntity<>(directorioProveedorService.obtenerDirectorioProveedorPorId(id), HttpStatus.OK);
    }

    @PutMapping("/directorio/{id}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
	"@seguridad.validarRolAdminSistemaSecundario() || " +
	"@seguridad.validarRolApoyoAcppi() || "  +
	"@seguridad.validarRolGestorTitutulos() || " +
	"@seguridad.validarRolLiderProyecto()")
    public ResponseEntity<DirectorioProveedorModel> actualizarDirectorio (@PathVariable Long id, @RequestBody DirectorioProveedorDto directorioProveedorDto){
        DirectorioProveedorModel directorioActualizado = directorioProveedorService.actualizaDirectorioContacto(id, directorioProveedorDto);
        return ResponseEntity.ok(directorioActualizado);

    }

    @DeleteMapping("/directorio/{id}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
	"@seguridad.validarRolAdminSistemaSecundario() || " +
	"@seguridad.validarRolApoyoAcppi()")
    public ResponseEntity<String> eliminarDirectorioContacto(@PathVariable Long id){
        directorioProveedorService.eliminarContactoDirectorio(id);
        return ResponseEntity.ok().build();
    }


}
