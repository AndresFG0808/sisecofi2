package com.sisecofi.reportedocumental.dto.papelera;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.TableJoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author omartinezj
 *
 */

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BusquedaPapelera {

	@TableJoin(priority = 1, tableJoin = "sisecofi.sscft_papelera", fieldJoin = "id_papelera", alias = "s1", orderyBy = "s1.id_papelera")
	private BusquedaPapeleraData busquedaDocumentalData;

}
