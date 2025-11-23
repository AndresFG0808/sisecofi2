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
public class BusquedaComiteDocumental {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_proyecto", fieldJoin = "id_proyecto", alias = "s1", orderyBy = "s1.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_proyecto", fieldJoin = "id_estatus_proyecto", alias = "s2", campos = {"s2.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_comite_proyecto", fieldJoin = "id_proyecto", alias = "s3", campos = {"s3.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_asociacion_comite_plantilla", fieldJoin = "id_comite_proyecto", alias = "s5",specificJoin = "s3", campos = {"s5.nombre","s7.descripcion" })
	@Joiny(tableJoin = "sisecofi.sscft_plantilla_vigente", fieldJoin = "id_plantilla_vigente", alias = "s4",specificJoin = "s5",  campos = {"s4.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_archivo_plantilla_comite", fieldJoin = "id_asociacion_comite_plantilla", alias = "s7",specificJoin = "s5", campos = {"s7.descripcion" })
	
	@Joiny(tableJoin = "sisecofi.sscfc_fase_proyecto", fieldJoin = "id_fase_proyecto", alias = "s10", campos = {"s10.nombre" },specificJoin = "s4")
	private BusquedaComiteDocumentalData busquedaComiteDocumentalData;
}
