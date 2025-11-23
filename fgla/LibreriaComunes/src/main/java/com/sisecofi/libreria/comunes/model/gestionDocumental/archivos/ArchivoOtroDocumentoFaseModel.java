package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otro_documento_fase")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@JsonTypeName("tipoFase")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtroDocumentoFaseModel extends Archivo {

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fase_proyecto", insertable = false, updatable = false)
    @JsonIgnore
    private CatFaseProyecto catFaseProyecto;
	
	@Column(name = "id_fase_proyecto")
    private Integer idFaseProyecto;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    @JsonIgnore
    private ProyectoModel proyectoModel;
	
	@Column(name = "id_proyecto")
    private Long idProyecto;

	@Override
	public String getFase() {
	    return (catFaseProyecto != null && catFaseProyecto.getNombre() != null)
	        ? catFaseProyecto.getNombre()
	        : "";
	}

	@Override
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
