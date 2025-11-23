package com.sisecofi.proyectos.service.adminplantrabajo.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import com.sisecofi.proyectos.service.adminplantrabajo.EnvioCorreoService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnvioCorreoServiceImpl implements EnvioCorreoService {

    private static final String TD = "</td>";

    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    public void enviarCorreoConTabla(List<Object[]> datosTabla) throws MessagingException {
        String asunto = "Calculo diario de carga plan de trabajo";
        String fechaCorte = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder cuerpo = new StringBuilder("<p>Se calculó exitosamente el Porcentaje de avance planeado y reales de los proyectos.</p>");
        cuerpo.append("<p>Fecha de corte: ").append(fechaCorte).append("</p>");

        if (datosTabla.isEmpty()) {
            cuerpo.append("<p>Aún no hay cálculos disponibles.</p>");
        } else {
            cuerpo.append("<table border='1'>");
            cuerpo.append("<tr><th>Id Proyecto</th><th>Fecha inicio del proyecto</th><th>Fecha fin del proyecto</th><th>Porcentaje planeado</th></tr>");

            for (Object[] fila : datosTabla) {
                Long idProyecto = (Long) fila[0];
                Date fechaInicioPlaneado = (Date) fila[1];
                Date fechaFinPlaneado = (Date) fila[2];
                Integer porcentajePlaneado = (Integer) fila[3];

                // Formatear las fechas SQL a LocalDate y luego aplicar el formato deseado
                String fechaInicioFormatted = fechaInicioPlaneado.toLocalDate().format(dateFormatter);
                String fechaFinFormatted = fechaFinPlaneado.toLocalDate().format(dateFormatter);

                cuerpo.append("<tr>");
                cuerpo.append("<td>").append(idProyecto).append(TD);
                cuerpo.append("<td>").append(fechaInicioFormatted).append(TD);
                cuerpo.append("<td>").append(fechaFinFormatted).append(TD);
                cuerpo.append("<td>").append(porcentajePlaneado).append(TD);
                cuerpo.append("</tr>");
            }

            cuerpo.append("</table>");

        }
        this.sendEmailTool(cuerpo.toString(), "pruebacalculo11@gmail.com", asunto);
    }

    private void sendEmailTool(String textMessage, String email, String subject) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setText(textMessage, Boolean.TRUE);
            helper.setSubject(subject);
            sender.send(message);
            log.debug("Mail enviado!");
        } catch (Exception e) {
            log.error("Hubo un error al enviar el mail:");
        }
    }
}
