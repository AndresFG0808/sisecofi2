package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NombreContratoDTO {
    private Long idContrato;
    private String nombreCorto;
    private boolean convenioColaboracion;

    public NombreContratoDTO() {
    }

    public NombreContratoDTO(Long idContrato, String nombreCorto, boolean convenioColaboracion) {
        this.idContrato = idContrato;
        this.nombreCorto = nombreCorto;
        this.convenioColaboracion = convenioColaboracion;
    }
}
