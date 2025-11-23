package com.sisecofi.reportedocumental.service.dinamico;

import org.apache.poi.ss.usermodel.Row;

import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface Mapper {

	void map(Object obj, BaseExcel baseExcel, Row row, int contador, Object[] t);

}
