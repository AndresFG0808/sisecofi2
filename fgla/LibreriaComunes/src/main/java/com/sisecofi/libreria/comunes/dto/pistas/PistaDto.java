package com.sisecofi.libreria.comunes.dto.pistas;

import java.time.LocalDateTime;

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
public class PistaDto {

	private Long idPista;
	private Integer idSeccionPista;
	private Integer idModuloPista;	
	private LocalDateTime fechaMovimiento;
	private String modulo;
	private String seccion;
	private String usuario;
	private String nombre;
	private String rfcLargo;
	private String tipoMovimiento;
	private String movimiento;
}
