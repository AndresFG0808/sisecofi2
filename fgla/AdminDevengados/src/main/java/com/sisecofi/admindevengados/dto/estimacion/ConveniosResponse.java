package com.sisecofi.admindevengados.dto.estimacion;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode( callSuper = false)
public class ConveniosResponse {
    private String nombreCorto;
    private String seleccionado;
    private List<String> convenios; 
}
