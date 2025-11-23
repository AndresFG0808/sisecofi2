package com.sisecofi.contratos.dto;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;

public class FacturaContratoResponse {
    private List<FacturaContratoDto> data;
    private String msj;

    // Getters y setters
    public List<FacturaContratoDto> getData() {
        return data;
    }

    public void setData(List<FacturaContratoDto> data) {
        this.data = data;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }
}
