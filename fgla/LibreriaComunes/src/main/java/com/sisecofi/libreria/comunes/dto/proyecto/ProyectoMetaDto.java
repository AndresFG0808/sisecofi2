package com.sisecofi.libreria.comunes.dto.proyecto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ProyectoMetaDto extends RepresentationModel<ProyectoMetaDto> {
	@EqualsAndHashCode.Include
	private Long idProyecto;
	private String nombreCorto;
	private CatEstatusProyecto estatus;
	private String liderProyecto;
	private CatAdmonCentral areaSolicitante;
	private CatAdministracion areaResponsable;
	private BaseCatalogoModel planTrabajo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private BigDecimal monto;
	@JsonIgnore
	private FichaTecnicaModel ficha;
}
