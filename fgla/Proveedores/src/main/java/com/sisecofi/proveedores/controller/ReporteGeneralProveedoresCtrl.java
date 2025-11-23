package com.sisecofi.proveedores.controller;



import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.proveedores.service.ReporteGeneralProveedoresService;
import com.sisecofi.proveedores.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ReporteGeneralProveedoresCtrl {

    private final ReporteGeneralProveedoresService reporteGeneralProveedoresService;

    @GetMapping("/reporte-general-proveedor")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
	"@seguridad.validarRolAdminSistemaSecundario() || " +
	"@seguridad.validarRolApoyoAcppi() || "  +
	"@seguridad.validarRolGestorTitutulos() || " +
	"@seguridad.validarRolUsuarioConsulta() || " +
	"@seguridad.validarRolLiderProyecto() || " +
	"@seguridad.validarRolAdministradorContrato() || " +
	"@seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolTodosProyectos()" )
    public ResponseEntity<byte[]> obtenerReporteGeneralProveedor(
        @RequestParam(required = false) Integer idGiroEmpresarial, 
        @RequestParam(required = false) Integer idTituloServicio,
        @RequestParam(required = false) Integer idCatResultadoDictamen){

    byte[] contenido = reporteGeneralProveedoresService.obtenerReporteGeneralProveedores(idGiroEmpresarial, idTituloServicio, idCatResultadoDictamen);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", "reporteProveedores.xlsx");

    return ResponseEntity.ok().headers(headers).body(contenido);




    }

}
