package com.sisecofi.libreria.comunes.dto.contrato;

import com.sisecofi.libreria.comunes.model.catalogo.CatConvenioColaboracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatDominiosTecnologicos;
import com.sisecofi.libreria.comunes.model.catalogo.CatFondeoContrato;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoProcedimiento;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatosGeneralesResponseDto extends DatosGeneralesContratoDto {

    private List<ProveedorModel> proveedores;
    private CatTipoProcedimiento catTipoProcedimiento;
    private CatDominiosTecnologicos catDominiosTecnologicos;
    private CatFondeoContrato catFondeoContrato;
    private CatConvenioColaboracion catConvenioColaboracion;


}
