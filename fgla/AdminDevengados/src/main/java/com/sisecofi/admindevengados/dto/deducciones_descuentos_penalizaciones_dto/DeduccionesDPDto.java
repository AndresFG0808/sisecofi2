package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import java.math.BigDecimal;



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
public class DeduccionesDPDto {

    private Long dictamenId;
    private Long idDdp;
    //CAT TIPO DESCUENTOS
    private Integer idTipoDescuento;
    private String nombreDescuento;
    private String moneda;
    private BigDecimal monto;
    private Integer ordenDescuento;

    

}
