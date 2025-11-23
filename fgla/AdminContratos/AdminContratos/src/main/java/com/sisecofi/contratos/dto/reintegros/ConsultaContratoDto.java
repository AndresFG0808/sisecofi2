package com.sisecofi.contratos.dto.reintegros;

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
public class ConsultaContratoDto {

    private String vigencia;
    private boolean todos;

}
