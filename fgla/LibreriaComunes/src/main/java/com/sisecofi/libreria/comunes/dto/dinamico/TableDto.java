package com.sisecofi.libreria.comunes.dto.dinamico;

import java.util.Set;

import com.sisecofi.libreria.comunes.util.anotaciones.reportes.Joiny;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.TableJoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Builder
@AllArgsConstructor
public class TableDto implements Comparable<TableDto> {

	private String alias;
	private TableJoin join;
	private Set<Joiny> joinys;

	@Override
	public int compareTo(TableDto o) {
		return Integer.valueOf(join.priority()).compareTo(Integer.valueOf(o.join.priority()));
	}



}
