package com.sisecofi.proveedores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.proveedores.service.ReporteTituloServicioProveedorService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class ReporteTituloServicioProveedorCtrl {

    @Autowired
    private ReporteTituloServicioProveedorService reporteTituloServicioProveedorService;

    @GetMapping("/reporte-titulos-servicio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
    "@seguridad.validarRolAdminSistemaSecundario() || " +
    "@seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<byte[]> obtenerReporteTituloServicioProveedor(@RequestParam("idProveedor") Long idProveedor){
        byte[] contenido = reporteTituloServicioProveedorService.obtenerReporteTituloServicioProveedor(idProveedor);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "repoteTituloServicioProveedor.xlsx");

        return ResponseEntity.ok().headers(headers).body(contenido);
    }





}
