package com.sisecofi.proveedores.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
import com.sisecofi.proveedores.service.CatTituloServicioService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
@Validated
public class CatTituloServicioCtrl {

    private final CatTituloServicioService tituloServicioService;

    @GetMapping("/consulta-titulo-servicio/{idServicioTitulo}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
    public CatTituloServicio obtenerTituloServicioPorId(@PathVariable Integer idServicioTitulo){
        return tituloServicioService.obtenerTituloServicioPorId(idServicioTitulo);
    }

    @GetMapping("/consultar-todos-titulos-servicios")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || "
			+ "@seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()"
			+ " ||@seguridad.validarRolGestorTitutulos()")
    public List<CatTituloServicio> obtenerTodosTitulosServicios(){
        return tituloServicioService.obtenerTodosTitulosServicios();
    }


}
