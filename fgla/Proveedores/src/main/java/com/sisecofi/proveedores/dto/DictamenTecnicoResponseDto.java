package com.sisecofi.proveedores.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class DictamenTecnicoResponseDto {

    private Long idDictamenTecnicoProveedor;
    private String anio;
    private String responsable;
    private String resultado;
    private String observacion;
    
    //Valores de ID
    private Integer idTituloServicio;
    private String nombreTituloServicio;


    private Integer idResultadoDictamenTecnico;

    @JsonIgnore
    private Long idProveedor;
    private Integer ordenDictamenProveedor;

}
