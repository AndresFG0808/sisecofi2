package com.sisecofi.admingeneral.dto.adminplantillas;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class PlantillaResponseDto {

	private Integer idPlantillaVigente;
	private CatFaseProyecto catFaseProyecto;
	private Integer idFaseProyecto;
	private String nombre;
	private LocalDateTime fecha;
	private LocalDateTime fechaModificacion;
	private String descripcion;
	private boolean estado;
	private boolean asignado;
	private String prefijo;
	

}
