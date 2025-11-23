package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otro_documento_convenio")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@JsonTypeName("tipoConvenio")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtroDocumentoConvenioModel  extends Archivo{
	
	@JoinColumn(name = "id_convenio_modificatorio", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ConvenioModificatorioModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ConvenioModificatorioModel convenio;

	@Column(name = "id_convenio_modificatorio")
	private Long idConvenioModificatorio;

	@Override
	public String getFase() {
		return "";
	}

	@Override
	public Long getIdProyecto() {
	    if (convenio != null &&
	        convenio.getContratoModel() != null &&
	        convenio.getContratoModel().getProyecto() != null) {
	        
	        return convenio.getContratoModel().getProyecto().getIdProyecto();
	    }
	    return null;
	}

	@Override
	public String getNombreCorto() {
	    if (convenio != null &&
	        convenio.getContratoModel() != null &&
	        convenio.getContratoModel().getProyecto() != null &&
	        convenio.getContratoModel().getProyecto().getNombreCorto() != null) {
	        
	        return convenio.getContratoModel().getProyecto().getNombreCorto();
	    }
	    return "";
	}


	@Override
	public String getPlantilla() {
		return "";
	}
}
