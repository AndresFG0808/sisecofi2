package com.sisecofi.admingeneral.controller.notificacion;

import com.sisecofi.libreria.comunes.dto.plantillador.NotificacionDTO;
import com.sisecofi.admingeneral.service.notificacion.NotificacionPagoService;
import com.sisecofi.admingeneral.util.Constantes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constantes.PATH_BASE)
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class NotificacionPagoController {

    NotificacionPagoService notificacionPagoService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "notificacion/pago")
    @PreAuthorize("@seguridad.validarRolAdminSistema()")
    public ResponseEntity<byte[]> generarNotificacionPago(@RequestBody NotificacionDTO request) {
        log.info("inicia generar la notificacion de pago");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("notificacion." + request.getTipoDocumento().toLowerCase()).build());
        var response = new ResponseEntity(notificacionPagoService.generarDocumento(request), headers, HttpStatus.OK);
        log.info("finaliza generar la notificacion de pago");
        return response;
    }
}
