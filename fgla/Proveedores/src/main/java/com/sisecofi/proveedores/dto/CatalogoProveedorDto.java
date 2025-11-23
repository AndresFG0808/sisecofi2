package com.sisecofi.proveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CatalogoProveedorDto {

    private Long idProveedor;
    private String nombreProveedor;
    private String rfc;

}
