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
public class BusquedaDictamenDocumental {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_proyecto", fieldJoin = "id_proyecto", alias = "s1", orderyBy = "s1.id_proyecto")
	@Joiny(tableJoin = "sisecofi.sscfc_estatus_proyecto", fieldJoin = "id_estatus_proyecto", alias = "s2", campos = {"s2.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_contrato", fieldJoin = "id_proyecto", alias = "s8", campos = {"s3.nombre","s7.descripcion" })
	@Joiny(tableJoin = "sisecofi.sscft_dictamen", fieldJoin = "id_contrato", alias = "s3",specificJoin = "s8", campos = {"s5.nombre","s7.descripcion" })
	@Joiny(tableJoin = "sisecofi.sscft_carpeta_plantilla_dictamen", fieldJoin = "id_dictamen", alias = "s5",specificJoin = "s3",  campos = {"s5.nombre","s7.descripcion" })	
	@Joiny(tableJoin = "sisecofi.sscft_archivo_plantilla_dictamen", fieldJoin = "id_carpeta_plantilla_dictamen", alias = "s7",specificJoin = "s5", campos = {"s7.descripcion" })
	
	@Joiny(tableJoin = "sisecofi.sscft_archivo_plantilla", fieldJoin = "id_archivo_base",fieldJoinReverse = "id_archivo_plantilla", alias = "s20",specificJoin = "s7", campos = {"s4.nombre" })
	@Joiny(tableJoin = "sisecofi.sscft_carpeta_plantilla", fieldJoin = "id_carpeta_plantilla", alias = "s21",specificJoin = "s20", campos = {"s4.nombre" })	
	@Joiny(tableJoin = "sisecofi.sscft_plantilla_vigente", fieldJoin = "id_plantilla_vigente", alias = "s4",specificJoin = "s21",  campos = {"s4.nombre" })
	@Joiny(tableJoin = "sisecofi.sscfc_fase_proyecto", fieldJoin = "id_fase_proyecto", alias = "s10", campos = {"s10.nombre" },specificJoin = "s4")
	private BusquedaDictamenDocumentalData busquedaDictamenDocumentalData;
}
