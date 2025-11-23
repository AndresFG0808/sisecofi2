package com.sisecofi.proveedores.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;

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
public class ConsultaTituloServicioProveedorDto {

    private Long idTituloServicioProveedor;
    private String numeroTitulo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date vencimientoTitulo;

    private String comentarios;
    private CatTituloServicio tituloServicioModel;
    private CatEstatusTituloServicio catEstatusTitulosServicioModel;
    private Long idProveedor;
    private String vigencia;




}
