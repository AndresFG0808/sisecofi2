package com.sisecofi.admindevengados.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.sisecofi.admindevengados.model.SolicitudFacturaModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudFacturaBanderaDto {

    private Long idSolicitudFactura;
    private Long dictamenId;
    private String noOficioSolicitud;

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime fechaSolicitud;

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime fechaRecepcionFactura;

    private Boolean banderaFactura;
    private String nombre;
    private String ruta;
    private Boolean tieneServicios;

    public SolicitudFacturaBanderaDto(SolicitudFacturaModel model, Boolean tieneServicios) {
        this.idSolicitudFactura = model.getIdSolicitudFactura();
        this.dictamenId = model.getDictamenId();
        this.noOficioSolicitud = model.getNoOficioSolicitud();
        this.fechaSolicitud = model.getFechaSolicitud();
        this.fechaRecepcionFactura = model.getFechaRecepcionFactura();
        this.banderaFactura = model.getBanderaFactura();
        this.nombre = model.getNombre();
        this.ruta = model.getRuta();
        this.tieneServicios = tieneServicios;

    }



}
