package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaProveedorDto {
    private Integer idProveedor;
    private String nombreProveedor;
    private String nombreComercial;
    private String rfc;
    private String direccion;
    private String comentarios;
    private Boolean estatus;
    private String idAgs;
}
