package com.sisecofi.libreria.comunes.dto.dictamen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenasContractualesByIdDto {
    private Long idPenaContractual;
    private Long idServicioSla;
    private Long idInfoDocServicios;
    private Long idInfoDocPeriodicos;
    private Long idInfoDocUnicaVez;
    private Long idAtraso;
}
