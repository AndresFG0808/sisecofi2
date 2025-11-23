package com.sisecofi.admingeneral.dto.adminplantillas;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class ProcesoCarpetaDto {

	private Row row;
	private List<Row> rows;
	private PlantillaVigenteModel plan;

}
