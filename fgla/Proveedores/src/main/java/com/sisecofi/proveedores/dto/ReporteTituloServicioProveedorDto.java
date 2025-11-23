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
public class ReporteTituloServicioProveedorDto {
    
    private Long idTituloServicioProveedor;
    private String numeroTitulo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date vencimientoTitulo;

    private String comentarios;
    private CatTituloServicioDto tituloServicioDto;
    private CatEstatusTituloServicioDto catEstatusTituloServicioDto;
    private String vigencia;
    private Long idProveedor;
    private Long ordenTitulo;


}
