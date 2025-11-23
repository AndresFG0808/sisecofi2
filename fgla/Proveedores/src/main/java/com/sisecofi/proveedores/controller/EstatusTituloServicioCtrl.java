package com.sisecofi.proveedores.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;
import com.sisecofi.proveedores.service.CatEstatusTitulosServicioService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
@Validated
public class EstatusTituloServicioCtrl {

    private final CatEstatusTitulosServicioService catEstatusTitulosServicioService;

    @GetMapping("/consultar-estatus-semaforo/{idCatEstatusTituloServicio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
    public CatEstatusTituloServicio obtenerEstatusPorId(@PathVariable Integer idCatEstatusTituloServicio){
        return catEstatusTitulosServicioService.obtenerEstatusPorId(idCatEstatusTituloServicio);
    }

    @GetMapping("/consultar-todos-estatus-semaforo")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
    public List<CatEstatusTituloServicio> obtenerTodosEstatus(){
        return catEstatusTitulosServicioService.obtenerTodosEstatus();
    }
    

}
