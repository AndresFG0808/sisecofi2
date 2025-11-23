package com.sisecofi.proveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DictamenTecnicoDto {

    private Long idDictamenTecnicoProveedor;
    private String responsable;

    ///cat servicio titulo
    private Integer idServicioTitulo;
    
    private String anio;
    private Integer idResultadoDictamenTecnico;
    private String observacion;
    private Long idProveedor;
    private Integer ordenDictamenProveedor;

}
