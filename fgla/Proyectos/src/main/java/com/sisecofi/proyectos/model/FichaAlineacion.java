package com.sisecofi.proyectos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatMapaObjetivo;
import com.sisecofi.libreria.comunes.model.catalogo.CatAliniacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodo;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "ficha_alineacion")
@Getter
@Setter
public class FichaAlineacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFichaAlineacion;
	
	@JoinColumn(name = "id_ficha_tecnica", insertable = false, updatable = false)
	@OneToOne(targetEntity = FichaTecnicaModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private FichaTecnicaModel fichaTecnicaModel;
	
	@Column(name = "id_ficha_tecnica")
	private Long idFichaTecnica;
	
	@JoinColumn(name = "id_mapa", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAliniacion.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatAliniacion catAliniacion;

	@Column(name = "id_mapa")
	private Integer idAliniacion;


	@JoinColumn(name = "id_periodo", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatPeriodo.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatPeriodo catPeriodo;

	@Column(name = "id_periodo")
	private Integer idPeriodo;

	@JoinColumn(name = "id_mapa_objetivo", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatMapaObjetivo.class, fetch = FetchType.LAZY)
	private CatMapaObjetivo catMapaObjetivo;

	@Column(name = "id_mapa_objetivo")
	private Integer idObjetivo;
	
	
	@Column(name = "estatusAlineacion")
	private boolean estatusAlineacion ;

}
