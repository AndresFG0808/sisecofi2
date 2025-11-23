package com.sisecofi.proveedores.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TituloServicioResponseDto {

    private Long idTituloServicioProveedor;
    private String numeroTitulo;
    private String nombreTituloServicio;
    private Integer idEstatusTituloServicio;
    private String semaforoEstatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date vencimientoTitulo;

    private String comentarios;
    private String vigencia;
    private String colorVigencia;
    
    @JsonIgnore
    private Long idProveedor;
    
    private Integer ordenTitulo;  
   
    

}
