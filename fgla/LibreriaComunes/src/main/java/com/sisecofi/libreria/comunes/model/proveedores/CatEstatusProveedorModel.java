package com.sisecofi.libreria.comunes.model.proveedores;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "estatusProveedor")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
public class CatEstatusProveedorModel extends BaseCatalogoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CampoFront(nombre = "Estatus ID", tipoDato = "Integer", orden = 1, id=true)
	@EqualsAndHashCode.Include
	private Integer idEstatusProveedor;

	@Override
	public Integer getPrimaryKey(){
		return idEstatusProveedor;
	}

}
