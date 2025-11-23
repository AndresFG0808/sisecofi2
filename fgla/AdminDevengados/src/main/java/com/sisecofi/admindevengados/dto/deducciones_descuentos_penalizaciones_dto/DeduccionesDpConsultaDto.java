package com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeduccionesDpConsultaDto {

    private Long idDdp;
    private String moneda;
    private Integer idTipoDescuento;
    private String nombreDescuento;
    private BigDecimal monto;
    //para identificar el origen de cada uno 
    private String  idOrigen;

}
