package com.sisecofi.libreria.comunes.model.proveedores;

import java.util.Objects;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "servicioProveedor")
@Getter
@Setter
public class CatServicioProveedorModel extends BaseCatalogoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idServicioProveedor;

	@Override
	public boolean equals (Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		if(!super.equals(o)) return false;
		CatServicioProveedorModel that = (CatServicioProveedorModel) o;
		return Objects.equals(idServicioProveedor, that.idServicioProveedor);
	}

	@Override
	public int hashCode(){
		return Objects.hash(super.hashCode(), idServicioProveedor);
	}
}
