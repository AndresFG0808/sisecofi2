package com.sisecofi.proveedores.controller;

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

import com.sisecofi.proveedores.dto.DictamenTecnicoDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;
import com.sisecofi.proveedores.service.DictamenTecnicoService;
import com.sisecofi.proveedores.util.Constantes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DictamenTecnicoCtrl {

    private final DictamenTecnicoService dictamenTecnicoService;


    @PostMapping("/crear-dictamen-tecnico")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<DictamenTecnicoResponseDto> crearDictamenTecnico(
            @Valid @RequestBody DictamenTecnicoDto dictamenTecnicoDto) {
        return new ResponseEntity<>(dictamenTecnicoService.crearDictamenTecnico(dictamenTecnicoDto), HttpStatus.CREATED);

    }

    @PostMapping("/crear-dictamen-tecnico-prueba")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
    "@seguridad.validarRolAdminSistemaSecundario() || " +
    "@seguridad.validarRolGestorTitutulos()")
public ResponseEntity<String> pruebaDictamen() {
    return ResponseEntity.ok("Endpoint funciona correctamente");
}

    @PutMapping("/actualizar-dictamen-tecnico/{idDictamenTecnicoProveedor}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<DictamenTecnicoResponseDto> actualizarDictamenTecnico(
            @PathVariable Long idDictamenTecnicoProveedor,
            @RequestBody DictamenTecnicoDto dictamenTecnicoDto) {
        DictamenTecnicoResponseDto dictamenTecnicoActualizado = dictamenTecnicoService
                .actaulizarDictamenTecnico(idDictamenTecnicoProveedor, dictamenTecnicoDto);
        return ResponseEntity.ok(dictamenTecnicoActualizado);

    }

    @GetMapping("/buscar-dictamen-tecnico/{idDictamenTecnicoProveedor}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<DictamenTecnicoResponseDto> consultarDictamenPorId(
            @PathVariable Long idDictamenTecnicoProveedor) {
        return new ResponseEntity<>(dictamenTecnicoService
                .consultarDictamenPorId(idDictamenTecnicoProveedor), HttpStatus.OK);
    }

    @DeleteMapping("eliminar-dictamen-tecnico/{idDictamenTecnicoProveedor}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolGestorTitutulos()")
    public ResponseEntity<String> eliminarDictamenTecnico(@PathVariable Long idDictamenTecnicoProveedor) {
        dictamenTecnicoService.eliminacionLogicaDictamenTecnico(idDictamenTecnicoProveedor);
        return ResponseEntity.ok().build();
    }
}
