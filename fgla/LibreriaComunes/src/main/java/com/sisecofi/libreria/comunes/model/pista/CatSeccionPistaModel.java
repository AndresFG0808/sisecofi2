package com.sisecofi.libreria.comunes.model.pista;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "seccionPista")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CatSeccionPistaModel extends BaseCatalogoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSeccionPista;

	@Column(name = "nombre", length = 150)
	private String nombre;

	@Column(name = "descripcion", length = 300)
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "id_modulo_pista", foreignKey = @ForeignKey(name = "FK_id_modulo_pista"), nullable = true)	
	@JsonBackReference
	private CatModuloPistaModel catModuloPistaModel;

	public CatSeccionPistaModel(Integer idSeccionPista) {
		super();
		this.idSeccionPista = idSeccionPista;
	}

	public CatSeccionPistaModel() {
		super();
	}

	@Override
	public Integer getPrimaryKey() {
		return idSeccionPista;
	}

}
