package com.sisecofi.proyectos.util.consumer.adminplantrabajo;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sisecofi.proyectos.repository.adminplantrabajo.PlanTrabajoRepository;
import com.sisecofi.proyectos.service.adminplantrabajo.EnvioCorreoService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class NotificacionPlaneado {

    private final PlanTrabajoRepository planTrabajoRepository;
    private final EnvioCorreoService envioCorreoService;

    @Scheduled(cron = "0 0 23 * * MON-FRI")
    private void notificarPlaneado() {


        List<Object[]> datosTabla = planTrabajoRepository.obtenerPorcentajePlaneado();

        try {
            envioCorreoService.enviarCorreoConTabla(datosTabla);
        } catch (MessagingException e) {
        	log.error("Error al enviar correo");
        }

    }

}
