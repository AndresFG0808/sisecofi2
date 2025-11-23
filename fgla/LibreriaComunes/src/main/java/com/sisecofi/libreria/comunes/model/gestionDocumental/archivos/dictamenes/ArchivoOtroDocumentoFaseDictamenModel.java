package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otro_documento_fase_dictamen")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@JsonTypeName("tipoFaseDictamen")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtroDocumentoFaseDictamenModel extends Archivo {

	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;

	@Override
	public String getFase() {
		return "Verificación";
	}

	@Override
	public Long getIdProyecto() {
		if (dictamen != null && dictamen.getContratoModel() != null
				&& dictamen.getContratoModel().getProyecto() != null) {

			return dictamen.getContratoModel().getProyecto().getIdProyecto();
		}
		return null;
	}

	@Override
	public String getNombreCorto() {
		if (dictamen != null && dictamen.getContratoModel() != null && dictamen.getContratoModel().getProyecto() != null
				&& dictamen.getContratoModel().getProyecto().getNombreCorto() != null) {

			return dictamen.getContratoModel().getProyecto().getNombreCorto();
		}
		return "";
	}

	@Override
	public String getPlantilla() {
		return "";
	}

}
