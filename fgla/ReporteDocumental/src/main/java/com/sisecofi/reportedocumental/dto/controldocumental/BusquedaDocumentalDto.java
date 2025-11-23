package com.sisecofi.reportedocumental.dto.controldocumental;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.TypeObject;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class BusquedaDocumentalDto implements GenericReport {

	@FilterField(filter = "s2.id_estatus_proyecto", type = TypeObject.TYPE_LIST)
	private List<Integer> idEstatusProyecto;

	@FilterField(filter = "s1.id_proyecto", type = TypeObject.TYPE_LIST)
	private List<Long> idProyecto;

	@FilterField(filter = "s7.descripcion", type = TypeObject.TYPE_STRING)
	private String documento;

	@FilterField(filter = "s10.id_fase_proyecto", type = TypeObject.TYPE_LIST)
	private List<Integer> idFase;

	@FilterField(filter = "s4.id_plantilla_vigente", type = TypeObject.TYPE_LIST)
	private List<Integer> idPlantilla;

	@FilterField(filter = "s7.estatus", type = TypeObject.TYPE_STRING)
	private static final String ESTATUS = "true";
	
	@JsonIgnore
	private BusquedaDocumental dataReporteDto;

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public int getPage() {
		return 1;
	}

	@Override
	public boolean isAcumulada() {
		return false;
	}

	@Override
	public Object getDataReporteDto() {
		return dataReporteDto;
	}

}
