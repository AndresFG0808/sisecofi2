package com.sisecofi.proveedores.dto;

import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Data
@AllArgsConstructor

public class ConsultaProveedorDto {

    private Long idProveedor;
    private String nombreProveedor;
    private String nombreComercial;
    private String rfc;
    private String direccion;
    private String comentarios;
    private Boolean estatus;
    private String idAgs;
    private CatGiroEmpresarial giroEmpresarialModel;
    private List<DirectorioProveedorDto> directorioContactos;
    private List<TituloServicioResponseDto> tituloServicioProveedor;
    private List<DictamenTecnicoResponseDto> dictamenTecnico;



}
