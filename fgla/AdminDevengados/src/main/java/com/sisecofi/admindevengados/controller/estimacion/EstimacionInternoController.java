package com.sisecofi.admindevengados.controller.estimacion;

import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sisecofi.admindevengados.service.estimacion.EstimacionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE_INTERNO)
public class EstimacionInternoController {
    private final EstimacionService estimacionService;

    @GetMapping("/estimacion/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema()")
    public ResponseEntity<List<EstimacionModel>> obtenerDictamenesAsociados(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(estimacionService.obtenerEstimacionPorIdContrato(idContrato), HttpStatus.OK);
    }
}
