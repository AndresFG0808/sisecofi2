package com.sisecofi.libreria.comunes.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ContatoDto {

    private Integer idContrato; //**
    private String nombreContrato; //**
    private String nombreProyecto; //---consultar db de proyectos
    private String numeroDeContrato;// normal
    private String proovedor; //---db ................... catalogo .... consultar poir id en microservicio
    private String tipoDeProcedimineto; //..?? DB --- jalar de tabla
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private String ultimoCm; //....?
    private BigDecimal montoMaximo; //---DB
    private BigDecimal montoMaximoUltimoCM;//---DB
    private BigDecimal montoMinimo; //---DB
    private String administracionCentral;//....? tabla db jalar o algun serv? mientras jalar...
    private String administracionDelContrato; //--? normal mientras o crear tabla para estos valores
}
