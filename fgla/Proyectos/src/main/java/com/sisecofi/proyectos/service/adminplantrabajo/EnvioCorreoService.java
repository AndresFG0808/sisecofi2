package com.sisecofi.proyectos.service.adminplantrabajo;

import java.util.List;


import jakarta.mail.MessagingException;




public interface EnvioCorreoService {


     void enviarCorreoConTabla(List<Object[]> datosTabla) throws MessagingException;


}
