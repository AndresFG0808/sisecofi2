package com.sisecofi.admindevengados.dto;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile; 


public record SolicitudFacturaUpdateDto(
        Long idDictamen,
        String noOficioSolicitud,
        LocalDate fechaSolicitud,
        MultipartFile documento,

        LocalDate fechaRecepcionFactura,
        Boolean banderaFactura
) { }
