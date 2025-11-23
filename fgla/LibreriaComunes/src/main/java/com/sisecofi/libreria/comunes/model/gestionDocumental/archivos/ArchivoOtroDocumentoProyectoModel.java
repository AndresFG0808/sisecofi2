package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otro_documento_proyecto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@JsonTypeName("tipoProyecto")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtroDocumentoProyectoModel  extends Archivo{
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    @JsonIgnore
    private ProyectoModel proyectoModel;
	
	@Column(name = "id_proyecto")
    private Long idProyecto;

	@Override
	public String getFase() {
		return "";
	}

	public Long getIdProyectoAux() {
		return idProyecto;
	}

	public String getNombreCorto() {
	    return (proyectoModel != null && proyectoModel.getNombreCorto() != null)
	        ? proyectoModel.getNombreCorto()
	        : "";
	}

	@Override
	public String getPlantilla() {
		return "";
	}


}
