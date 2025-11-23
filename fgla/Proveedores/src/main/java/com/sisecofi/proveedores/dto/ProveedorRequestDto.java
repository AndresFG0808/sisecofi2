package com.sisecofi.proveedores.dto;

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
public class ProveedorRequestDto {


    private Integer idGiroEmpresarial;
    private Integer idTituloServicio;
    private Integer idCatResultadoDictamen;
    private Integer idProveedor;
    private int page = 0;
    private int size = 15;

    private static final int DEFAULT_SIZE = 15;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 100;


    public void setSize(int size){
        if(size < MIN_SIZE || size > MAX_SIZE){
            this.size = DEFAULT_SIZE;
        }else{
            this.size = size;
        }
    }


}
