package com.sisecofi.libreria.comunes.dto.reportedinamico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoNombreDto {

	private Long idContrato;

	private String nombreCorto;

}
