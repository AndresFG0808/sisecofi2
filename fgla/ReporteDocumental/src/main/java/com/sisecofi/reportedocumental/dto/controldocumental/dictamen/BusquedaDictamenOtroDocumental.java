package com.sisecofi.reportedocumental.dto.controldocumental.dictamen;

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
public class BusquedaDictamenOtroDocumental {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_proyecto", fieldJoin = "id_proyecto", alias = "s1", orderyBy = "s1.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_proyecto", fieldJoin = "id_estatus_proyecto", alias = "s2", campos = {"s2.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_contrato", fieldJoin = "id_proyecto", alias = "s9", campos = {"s5.nombre","s7.descripcion" })
	@Joiny(tableJoin = "sisecofi.sscft_dictamen", fieldJoin = "id_contrato", alias = "s8",specificJoin = "s9",  campos = {"s5.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_archivo_otro_documento_dictamen", fieldJoin = "id_dictamen", alias = "s7",specificJoin = "s8", campos = {"s7.descripcion" })	
	private BusquedaDictamenOtroDocumentalData busquedaDictamenOtroDocumentalData;
}
