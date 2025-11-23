package com.sisecofi.reportedocumental.dto.pistareporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pistas {

	private int idPista;
	private String modulo;
	private String seccion;
	private String fechaHora;
	private String empleado;
	private String rfc;
	private String tipoMovimiento;

}
