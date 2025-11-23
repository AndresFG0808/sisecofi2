package com.sisecofi.reportedocumental.dto.controldocumental.comite;

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
public class BusquedaComiteOtroDocumental {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_proyecto", fieldJoin = "id_proyecto", alias = "s1", orderyBy = "s1.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_proyecto", fieldJoin = "id_estatus_proyecto", alias = "s2", campos = {"s2.nombre","s7.descripcion" })		
	@Joiny(tableJoin = "sisecofi.sscft_comite_proyecto", fieldJoin = "id_proyecto", alias = "s4",  campos = {"s4.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_archivo_otros_documentos_comite", fieldJoin = "id_comite_proyecto", alias = "s7",specificJoin = "s4", campos = {"s7.descripcion" })	
	private BusquedaComiteOtroDocumentalData busquedaComiteNoDocumentalData;
}
