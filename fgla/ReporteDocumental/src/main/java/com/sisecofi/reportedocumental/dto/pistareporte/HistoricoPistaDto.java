package com.sisecofi.reportedocumental.dto.pistareporte;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class HistoricoPistaDto {

	private String detalleMovimiento;

	private String detalleMovimientoAnterior;

	private String ultimaModificacion;
}
