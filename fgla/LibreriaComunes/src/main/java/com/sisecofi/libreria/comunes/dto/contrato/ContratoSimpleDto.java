package com.sisecofi.libreria.comunes.dto.contrato;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContratoSimpleDto {
    private Long idContrato;
    private String nombreCorto;
    private Integer idEstatusContrato;
    private boolean ejecucion; 

    public ContratoSimpleDto(Long idContrato, String nombreCorto, Integer idEstatusContrato) {
        this.idContrato = idContrato;
        this.nombreCorto = nombreCorto;
        this.idEstatusContrato = idEstatusContrato;
    }

    public ContratoSimpleDto() {
    }
}