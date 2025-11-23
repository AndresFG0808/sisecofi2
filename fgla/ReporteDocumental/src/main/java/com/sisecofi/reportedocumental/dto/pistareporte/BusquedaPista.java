package com.sisecofi.reportedocumental.dto.pistareporte;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.Joiny;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.TableJoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BusquedaPista {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscfb_pista", fieldJoin = "id_pista", alias = "s1",orderyBy = "s1.id_pista")
	@Joiny(tableJoin = "sisecofi.sscfc_modulo_pista", fieldJoin = "id_modulo_pista", alias = "s2", campos = {"s2.nombre" })
	@Joiny(tableJoin = "sisecofi.sscfc_seccion_pista", fieldJoin = "id_seccion_pista", alias = "s3", campos = {"s3.nombre" })	
	@Joiny(tableJoin = "sisecofi.sscfc_tipo_mov_pista", fieldJoin = "id_tipo_mov_pista", alias = "s4", campos = {"s4.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_usuario", fieldJoin = "id_user", alias = "s5", campos = {"s5.nombre","s5.rfc_largo" })	
	private PistaData pistaData;
		
	
}
