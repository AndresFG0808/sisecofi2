package com.sisecofi.proveedores.dto;

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
public class TituloServicioNumeroTituloDto {

    private Long idTituloServicioProveedor;
    private String numeroTitulo;

}
