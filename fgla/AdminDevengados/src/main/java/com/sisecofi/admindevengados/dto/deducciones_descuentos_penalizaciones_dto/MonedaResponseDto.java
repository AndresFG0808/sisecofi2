package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonedaResponseDto {

    private String moneda;
    private String idOrigen;

}
