package com.sisecofi.catalogos.dto;

import java.util.Map;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class CatalogoMetaDto extends RepresentationModel<CatalogoMetaDto> {

	private Integer idCatalogo;
	private String catalogo;
	@JsonInclude(Include.NON_NULL)
	private String clase;
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> metaData;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(idCatalogo);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) 
	        return true;
	    if (!(obj instanceof CatalogoMetaDto) || !super.equals(obj))
	        return false;
	    CatalogoMetaDto other = (CatalogoMetaDto) obj;
	    return Objects.equals(idCatalogo, other.idCatalogo);
	}

}
