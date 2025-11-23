package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;

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
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otro_documento_fase_reintegro")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@JsonTypeName("tipoFaseReintegro")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtroDocumentoFaseReintegroModel extends Archivo {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_reintegros_asociados", insertable = false, updatable = false)
	@JsonIgnore
	private ReintegrosAsociadosModel reintegro;

	@Column(name = "id_reintegros_asociados")
	private Long idReintegrosAsociados;

	@Override
	public String getFase() {
		return "Reintegros";
	}

	@Override
	public Long getIdProyecto() {
		if (reintegro != null && reintegro.getContratoModel() != null
				&& reintegro.getContratoModel().getProyecto() != null) {

			return reintegro.getContratoModel().getProyecto().getIdProyecto();
		}
		return null;
	}

	@Override
	public String getNombreCorto() {
		if (reintegro != null && reintegro.getContratoModel() != null
				&& reintegro.getContratoModel().getProyecto() != null
				&& reintegro.getContratoModel().getProyecto().getNombreCorto() != null) {

			return reintegro.getContratoModel().getProyecto().getNombreCorto();
		}
		return "";
	}

	@Override
	public String getPlantilla() {
		return "";
	}

}
