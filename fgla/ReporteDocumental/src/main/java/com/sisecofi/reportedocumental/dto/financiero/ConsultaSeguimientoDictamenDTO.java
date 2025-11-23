package com.sisecofi.reportedocumental.dto.financiero;

import lombok.Getter;
import lombok.Setter;

import java.util.List;



import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ConsultaSeguimientoDictamenDTO extends PaginadorDTO {
    private List<Integer> listaIdEstatusDictamen;

    private Integer idContratoVigente; // 1 (Sí), 2 (No), 3 (Todos)

   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer idVerificadorContrato;
    

    private String nombreCortoProyecto;

    private String nombreCortoContrato;
}