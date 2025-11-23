package com.sisecofi.proveedores.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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



public class ProveedoGeneralDto {

    private Long idProveedor;
    private String nombreProveedor;
    private String nombreComercial;
    private String idAgs;
    private String giroDeLaEmpresa;
    private String rfc;
    private String representanteLegal;
    private String tituloDeServicio;
    private String vigencia;
    private String colorVigencia;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date vencimientoTitulo;
    
    private Boolean estatus;
    private String cumpleDictamen;



    
}
