package com.sisecofi.catalogos.dto;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Getter
@Setter
public class ReponseCatalogo extends RepresentationModel<ReponseCatalogo> {

	@JsonProperty("catalogo")
	private CatalogoMetaDto catalogoMetaDto;
	@JsonProperty("hijos")
	private Integer hijos;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Integer idCatalogoComplementario;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(catalogoMetaDto);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    if (!super.equals(obj)) {
	        return false;
	    }
	    ReponseCatalogo other = (ReponseCatalogo) obj;
	    return Objects.equals(catalogoMetaDto, other.catalogoMetaDto);
	}


}
