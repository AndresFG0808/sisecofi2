package com.sisecofi.libreria.comunes.dto.contrato;

import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContratoProveedorDto {
   private ProveedorModel proveedor;
   
   private ContratoModel contrato;
   
   private Integer idIva;

}
