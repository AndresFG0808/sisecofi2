package com.sisecofi.libreria.comunes.model.proyectos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

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
@Table(name = ConstantesComunes.PREFIX_REL + "ficha_tecnica_admon_central")
@Getter
@Setter
public class FichaAdmonCentral {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "id_ficha_tecnica", insertable = false, updatable = false)
	@OneToOne(targetEntity = FichaTecnicaModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private FichaTecnicaModel fichaTecnicaModel;
	
	@Column(name = "id_ficha_tecnica")
	private Long idFichaTecnica;
	
	@JoinColumn(name = "id_admon_central", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdmonCentral.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatAdmonCentral catAdmonCentral;

	@Column(name = "id_admon_central")
	private Integer idAdmonCentral;	
	
	@Column(name = "estatus")
	private boolean estatus;	
	
	
	@JoinColumn(name = "id_administrador_central", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdministradorCentral.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatAdministradorCentral catAdministradorCentral;

	@Column(name = "id_administrador_central")
	private Integer idAdministradorCentral;	
	
}
