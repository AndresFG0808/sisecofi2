package com.sisecofi.proveedores.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor

public class TituloServicioProveedorDto {

    private Long idTituloServicioProveedor;
    private String numeroTitulo;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String vencimientoTitulo;

    private String comentarios;
    private Integer idServicioTitulo; // titulos de servicio
    private Integer idEstatusTituloServicio; // catalogo de estatus

    @JsonIgnore
    private String colorSemaforoEstatus;
    
    private Long idProveedor;

    private String vigencia;

    private String colorVigencia;

    private Integer ordenTitulo;
}
