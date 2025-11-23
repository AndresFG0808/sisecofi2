package com.sisecofi.admingeneral.dto.adminplantillas;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class CarpetaDto {

	private String nombre;
	private int orden;
	private int nivel;
	private String tipo;
	private boolean obligatorio;
	private boolean estatus;
	private String descripcion;
	@JsonInclude(Include.NON_NULL)
	private Integer idCatalogo;
	@JsonInclude(Include.NON_NULL)
	private List<CarpetaPlantillaModel> carpetas;
	@JsonInclude(Include.NON_NULL)
	private List<ArchivoPlantillaModel> archivos;

	@Override
	public String toString() {
		return "CarpetaDto [nombre=" + nombre + ", orden=" + orden + ", nivel=" + nivel + "]";
	}

}
