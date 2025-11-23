package com.sisecofi.libreria.comunes.model.pista;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = ConstantesComunes.PREFIX_CAT + "tipoMovPista")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CatTipoMovPistaModel extends BaseCatalogoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipoMovPista;

	@Column(name = "nombre", length = 150)
	private String nombre;

	@Column(name = "descripcion", length = 300)
	private String descripcion;

	public CatTipoMovPistaModel(Integer idTipoMovPista) {
		super();
		this.idTipoMovPista = idTipoMovPista;
	}

	public CatTipoMovPistaModel() {
		super();
	}

	@Override
	public Integer getPrimaryKey() {
		return idTipoMovPista;
	}

}
