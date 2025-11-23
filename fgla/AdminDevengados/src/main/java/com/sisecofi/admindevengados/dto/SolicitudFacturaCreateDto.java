package com.sisecofi.admindevengados.dto;


import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile; 

public record SolicitudFacturaCreateDto(
        @NotNull(message = "El campo idDictamen es requerido") Long idDictamen,
        @NotNull(message = "El campo noOficioSolicitud es requerido") String noOficioSolicitud,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) LocalDate fechaSolicitud,
        MultipartFile documento
) { }
