package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProveedoresDto {
    private  Long idContrato;
    private List<Long> idsProveedores;
}
