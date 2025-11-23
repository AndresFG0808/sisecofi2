package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

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
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otro_documento_contrato")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@JsonTypeName("tipoContrato")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtroDocumentoContratoModel  extends Archivo{
	
	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ContratoModel contratoModel;

	@Column(name = "id_contrato")
	private Long idContrato;

	@Override
	public String getFase() {
		return "";
	}

	@Override
	public Long getIdProyecto() {
	    if (contratoModel != null && contratoModel.getProyecto() != null) {
	        return contratoModel.getProyecto().getIdProyecto();
	    }
	    return null;
	}

	@Override
	public String getNombreCorto() {
	    if (contratoModel != null &&
	        contratoModel.getProyecto() != null &&
	        contratoModel.getProyecto().getNombreCorto() != null) {
	        
	        return contratoModel.getProyecto().getNombreCorto();
	    }
	    return "";
	}

	@Override
	public String getPlantilla() {
		return "";
	}
}
