package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LayoutInformesDto {
    private Long idContrato;
    private Integer idSeccionLayout;
    private String nombre;
    private String archivo;
}
