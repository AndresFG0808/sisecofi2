package com.sisecofi.proveedores.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Setter;


/**
 * @author adtolentino
 * 
 * 
 */


@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DirectorioProveedorDto {

    private Long idDirectorioContacto;   
    private String nombreContacto;
    private String telefonoOficina;
    private String telefonoCelular;
    private String correoElectronico;
    private String comentarios;
    private Boolean representanteLegal;
    
    @JsonIgnore
    private boolean estatus;

    private Long idProveedor;
    private Integer ordenDirectorio;

    

}
