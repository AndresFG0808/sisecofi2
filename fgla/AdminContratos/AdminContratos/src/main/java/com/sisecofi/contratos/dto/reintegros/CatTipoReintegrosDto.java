package com.sisecofi.contratos.dto.reintegros;

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
public class CatTipoReintegrosDto {
    
    private Integer idTipoReintegro;
    private String nombre;

}
