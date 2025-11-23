package com.sisecofi.proveedores.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author adtolentino
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor

public class ProveedorDto {

    private String nombreProveedor;
    private String nombreComercial;
    private String rfc;
    private String direccion;
    private String comentarios;
    private Boolean estatus;
    private String idAgs;
    private Long idGiroEmpresarial;
    private List<DirectorioProveedorDto> directoriosContactos;
    private List<TituloServicioProveedorDto> tituloServicioProveedor;
    private boolean duplicado;

}
