package com.sisecofi.admingeneral.dto.plantillador;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PlantilladorDto {

	private Long idSubTipoPlantillador;
	private Integer idTipoPlantillador;
	private String nombre;
	private String comentarios;
	private LocalDateTime fechaModificacion;
	private boolean estatus;
	private String tipo;
	private Long idPlantillador;
	private Long idSubPlantillador;
	private List<PlantilladorDto> subRows;
	private String maxVersion;
	private String version;
	private Long idContenidoSubPlantillador;
	private Long idTipo;
}
