package com.sisecofi.proveedores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;
import com.sisecofi.proveedores.service.GiroEmpresarialService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;


/**
 * @author adtolentino
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
@Validated
public class GiroEmpresarialCtrl {

    @Autowired
    private final GiroEmpresarialService giroEmpresarialService;

    @GetMapping("/consultar-giro/{id}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
    public CatGiroEmpresarial obtenerGiroPorId(@PathVariable Long id) {
        return giroEmpresarialService.obtenerGiroPorId(id);

    }

    @GetMapping("/consultar-todos-giros")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
    public List<CatGiroEmpresarial> obtenertodosGirosEmpresariales() {
        return giroEmpresarialService.obtenerTodosLosGiros();

    }

}
