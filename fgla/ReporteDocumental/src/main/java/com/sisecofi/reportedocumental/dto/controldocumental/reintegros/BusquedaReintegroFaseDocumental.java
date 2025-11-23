package com.sisecofi.reportedocumental.dto.controldocumental.reintegros;

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
public class BusquedaReintegroFaseDocumental {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_proyecto", fieldJoin = "id_proyecto", alias = "s1", orderyBy = "s1.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_proyecto", fieldJoin = "id_estatus_proyecto", alias = "s2", campos = {"s2.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_contrato", fieldJoin = "id_proyecto", alias = "s8", campos = {"s3.nombre","s7.descripcion" })
	@Joiny(tableJoin = "sisecofi.sscft_reintegros_asociados", fieldJoin = "id_contrato", alias = "s3",specificJoin = "s8",  campos = {"s4.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_archivo_otro_documento_fase_reintegro", fieldJoin = "id_reintegros_asociados", alias = "s7",specificJoin = "s3", campos = {"s7.descripcion" })	
	private BusquedaReintegroFaseDocumentalData busquedaReintegroFaseDocumentalData;
}
