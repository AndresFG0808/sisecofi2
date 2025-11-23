package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ObtenerPenaContractualRequestDto {

    private Long idPenaPrimary;
    private Long dictamenId;
    private Long idTipoPena;
    private Long idDesglose;
    private Long idDocumento;
    private BigDecimal monto;
    private String conceptoServicio;
    private String penaAplicable;
    private String tipoPena;

}
