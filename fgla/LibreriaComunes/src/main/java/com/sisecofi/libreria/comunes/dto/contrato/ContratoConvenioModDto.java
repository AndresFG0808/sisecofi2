package com.sisecofi.libreria.comunes.dto.contrato;

import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContratoConvenioModDto {
   
   private ContratoModel contrato;
   
   private ConvenioModificatorioModel cm;

}
