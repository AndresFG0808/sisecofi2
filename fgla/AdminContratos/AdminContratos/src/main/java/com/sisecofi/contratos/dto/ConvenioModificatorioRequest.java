package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvenioModificatorioRequest {
    private Integer size;
    private Integer page;
    private Long idContrato;
}
